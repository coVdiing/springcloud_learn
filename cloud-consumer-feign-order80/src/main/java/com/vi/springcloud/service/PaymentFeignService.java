package com.vi.springcloud.service;

import com.vi.springcloud.config.FeignConfig;
import com.vi.springcloud.entities.CommonResult;
import com.vi.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="CLOUD-PAYMENT-SERVICE",configuration = FeignConfig.class)
public interface PaymentFeignService {
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();
}
