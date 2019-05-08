package snod.com.cn.iot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig  {
	
	@Bean
    public IotListener aListener(){
        return new IotListener();
    }
}
