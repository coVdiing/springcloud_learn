package com.vi.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRule {
    @Bean
    public IRule randomRule() {
        //将一个定义的IRule接口实现类加入到容器中，用于替换默认的轮询规则:RoundRobinRule
        return new IRuleImpl();
    }
}
