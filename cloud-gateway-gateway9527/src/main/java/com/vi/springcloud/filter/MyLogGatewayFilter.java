package com.vi.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**** come in MyLogGatewayFilter :"+ ZonedDateTime.now());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        Map paramMap = exchange.getRequest().getQueryParams();
        Set keys = paramMap.keySet();
        for (Object key : keys) {
            log.info("**** {} ----> {} ****",key,paramMap.get((String)key));
        }
        if (uname == null) {
            log.info("**** 用户名为null，非法用户访问!!");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
