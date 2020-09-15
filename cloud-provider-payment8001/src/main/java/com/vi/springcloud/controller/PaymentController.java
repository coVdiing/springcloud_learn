package com.vi.springcloud.controller;

import com.vi.springcloud.entities.CommonResult;
import com.vi.springcloud.entities.Payment;
import com.vi.springcloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult create(Payment payment) {
       int result =  paymentService.create(payment);
        if (result == 1) {
            return new CommonResult(200, "创建订单成功", result);
        } else {
            return new CommonResult(444, "创建订单失败", result);
        }
    }

    @GetMapping("/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return new CommonResult(200, "获取订单成功", payment);
        } else {
            return new CommonResult(444, "获取订单" + id + "失败", null);
        }
    }
}
