package snod.com.cn.iot.test;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;

public class subTest {
	
	
	public static void main(String[] args) {
		// 阿里云accessKey
        String accessKey = "LTAIfZB0R23btV3j";
        // 阿里云accessSecret
        String accessSecret ="J8B8NMFHq901PASSn86vnfkFO7gL8A";
        // regionId
        String regionId = "cn-shanghai";
        // 阿里云uid
        String uid = "1425203365193024";
        // endPoint:  https://${uid}.iot-as-http2.${region}.aliyuncs.com
        String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";

        // 连接配置
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);

        // 构造客户端
        MessageClient client = MessageClientFactory.messageClient(profile);
       
        // 数据接收
        client.connect(messageToken -> {
            Message m = messageToken.getMessage();
            System.out.println("receive message from " + m);
            System.out.println(new String(m.getPayload()));
            return MessageCallback.Action.CommitSuccess;
        });
	}
	
}