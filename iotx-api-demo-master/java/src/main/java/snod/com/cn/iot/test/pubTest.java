package snod.com.cn.iot.test;

import org.apache.commons.codec.binary.Base64;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20180120.PubRequest;
import com.aliyuncs.iot.model.v20180120.PubResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import snod.com.cn.iot.util.LogUtil;

public class pubTest {
	
	public static void main(String[] args) {
		String accessKeyID="LTAIfZB0R23btV3j";
		String accessKeySecret="J8B8NMFHq901PASSn86vnfkFO7gL8A";
		String regionId="cn-shanghai";
		// 阿里云accessKey
        String productKey = "a1lK0waMzT5";
        // 阿里云accessSecret
        String topic ="/a1lK0waMzT5/device_two/get";
        String msg = "hello world";
    	try {
			IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyID, accessKeySecret);
			DefaultProfile.addEndpoint(regionId, regionId,"Iot","iot.cn-shanghai.aliyuncs.com");
			// 初始化client
			DefaultAcsClient client = new DefaultAcsClient(profile);
			PubRequest request = new PubRequest();
	        request.setProductKey(productKey);
	        request.setTopicFullName(topic);
	        request.setMessageContent(Base64.encodeBase64String(msg.getBytes()));
	        request.setQos(1);
	        PubResponse response = client.getAcsResponse(request);
	        if (response != null && response.getSuccess() != false) {
	            LogUtil.print("发送消息成功！messageId：" + response.getMessageId());
	        } else {
	            LogUtil.error("发送消息失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
	        }
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
