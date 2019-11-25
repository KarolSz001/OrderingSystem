package com.app.repo.impl;


import com.app.model.Payment;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.PaymentRepository;

public class PaymentRepositoryImpl extends AbstractCrudRepository<Payment,Long> implements PaymentRepository {
    public PaymentRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }
}
