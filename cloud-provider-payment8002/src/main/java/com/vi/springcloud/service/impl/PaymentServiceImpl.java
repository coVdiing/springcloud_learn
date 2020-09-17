package com.vi.springcloud.service.impl;

import com.vi.springcloud.dao.PaymentDao;
import com.vi.springcloud.entities.Payment;
import com.vi.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        int result = paymentDao.create(payment);
        return result;
    }

    @Override
    public Payment getPaymentById(Long id) {
        Payment payment = paymentDao.getPaymentById(id);
        return payment;
    }
}
