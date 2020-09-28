package com.vi.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + " ,paymentInfo_OK,id " + id + " ^_^ ";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id) {
//        int i = 10/0;
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:" + Thread.currentThread().getName() + " ,paymentInfo_TimeOut,id " + id + " v_v''' ";
    }


    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池:" + Thread.currentThread().getName() + " ,8001系统繁忙或出错，请稍后再试! o(╥﹏╥)o";
    }

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  // 是否开启断路器,默认是
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value="10"), // 滑动窗口的大小，默认为20（请求次数）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value="10000"), // 过多长时间，熔断器再次检测是否开启，默认为5000，即5s钟
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60") // 失败率达到多少触发熔断,默认为50
    })
    public String paymentCircuitBreaker(Integer id) {

        if (id < 0) {
            throw new RuntimeException("**** id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t调用成功，流水号为:" + serialNumber;
    }

    // 达到熔断以后设置的请求处理方法
    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数,请稍后再试，o(╥﹏╥)o id:" + id;
    }
}
