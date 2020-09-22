package com.vi.springcloud.controller;

import com.vi.myrule.MyRule;
import com.vi.springcloud.entities.CommonResult;
import com.vi.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
@RibbonClient(name = "cloud-payment-service",configuration = MyRule.class)
public class OrderController {
    //public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        log.info("****创建订单:" + payment);
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("****查询订单:" + id);
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentEntityById(@PathVariable("id") Long id) {
        log.info("****查询订单:" + id);
         ResponseEntity<CommonResult> result = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (result.getStatusCode().is2xxSuccessful()) {
            log.info("****entity:{},{}",result.getStatusCodeValue(),result.getHeaders());
            return result.getBody();
        } else {
            return new CommonResult<>(444, "查询失败");
        }
    }
}
