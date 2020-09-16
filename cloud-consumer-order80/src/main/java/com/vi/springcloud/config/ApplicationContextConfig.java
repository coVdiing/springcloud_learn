package com.vi.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    //将RestTemplate的对象加入到ioc容器中，用于服务之间的调用
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
