package com.vi.springcloud.controller;

import com.vi.springcloud.entities.CommonResult;
import com.vi.springcloud.entities.Payment;
import com.vi.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    PaymentService paymentService;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
       int result =  paymentService.create(payment);
       log.info("****插入结果:"+result);
        if (result == 1) {
            return new CommonResult(200, "插入数据库成功", result);
        } else {
            return new CommonResult(444, "插入数据库失败", result);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****查询结果:"+payment);
        if (payment != null) {
            return new CommonResult(200, "获取订单成功", payment);
        } else {
            return new CommonResult(444, "获取订单" + id + "失败", null);
        }
    }
}
