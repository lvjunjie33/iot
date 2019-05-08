package snod.com.cn.iot.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;

public class IotListener  implements ApplicationListener<ContextRefreshedEvent>{

	@Value(value = "${aliyun.iot.accessKey}")
	private String accessKey;
	@Value(value = "${aliyun.iot.accessSecret}")
	private String accessSecret;
	@Value(value = "${aliyun.iot.regionId}")
	private String regionId;
	@Value(value = "${aliyun.iot.uid}")
	private String uid;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {	
        String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";
        // 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);
        // 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);
        // 数据接收
        client.connect(messageToken -> {
        	Message m = messageToken.getMessage();
        	if(m.toString().contains("online")) {//开启连接
        		executeOnline(m);
        	}else if(m.toString().contains("offline")) {//关闭连接
    			executeOffline(m);
        	}else {
        		//接收设备消息
        		execute(m);
        	}
        	return MessageCallback.Action.CommitSuccess;
     
        });
	}
	 public void executeOnline(Message m) {
		String s=new String(m.getPayload());
 		JSONObject json= JSON.parseObject(s);
 		String methodName=json.getString("status");
 		String deviceName=json.getString("deviceName");
 		String productKey=json.getString("productKey");
 		reflex(productKey,deviceName,methodName,new String(m.getPayload()));
     }
	 public void execute(Message m) {
		 String str[]=m.getTopic().split("/");
         String productKey=str[1];
         String deviceName=str[2];
         String methodName=str[3];
         reflex(productKey,deviceName,methodName,new String(m.getPayload()));
     }
	 public void executeOffline(Message m) {
		 String s=new String(m.getPayload());
		 JSONObject json= JSON.parseObject(s);
		 String methodName=json.getString("status");
		 String deviceName=json.getString("deviceName");
		 String productKey=json.getString("productKey");
		 reflex(productKey,deviceName,methodName,new String(m.getPayload()));
     }
	 
	 public void reflex(String productKey,String deviceName,String methodName,String param) {
		try {
			Class clazz = Class.forName("snod.com.cn.iot.device."+productKey+"."+deviceName);
			Method method = clazz.getDeclaredMethod(methodName, String.class);
			Object obje;
			obje = clazz.newInstance();
			method.invoke(obje,param);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
}