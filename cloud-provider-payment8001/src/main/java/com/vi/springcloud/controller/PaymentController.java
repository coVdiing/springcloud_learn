package com.vi.springcloud.controller;


import com.vi.springcloud.entities.CommonResult;
import com.vi.springcloud.entities.Payment;
import com.vi.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
       int result =  paymentService.create(payment);
       log.info("****插入结果:"+result);
        if (result == 1) {
            return new CommonResult(200, "插入数据库成功,port:"+port, result);
        } else {
            return new CommonResult(444, "插入数据库失败,port:"+port, result);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****查询结果:"+payment);
        if (payment != null) {
            return new CommonResult(200, "获取订单成功,port:"+port, payment);
        } else {
            return new CommonResult(444, "获取订单" + id + "失败,port:"+port, null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services =  discoveryClient.getServices();
        for (String service : services) {
            log.info("****service:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("****instance:{},{},{}",instance.getInstanceId(),instance.getHost(),instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout() {
        //模拟耗时较长的复杂业务
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }
}
