package com.vi.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "80客户端服务降级类ok方法:系统繁忙请稍后再试 ╮(╯▽╰)╭";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "80客户端服务降级类timeout方法:系统繁忙请稍后再试 ╮(╯▽╰)╭";
    }
}
