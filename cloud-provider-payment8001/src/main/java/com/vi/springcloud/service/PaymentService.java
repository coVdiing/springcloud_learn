package com.vi.springcloud.service;

import com.vi.springcloud.entities.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(Long id);
}
