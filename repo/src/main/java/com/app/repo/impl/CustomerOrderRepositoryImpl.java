package com.app.repo.impl;

import com.app.model.CustomerOrder;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CustomerOrderRepository;


public class CustomerOrderRepositoryImpl extends AbstractCrudRepository<CustomerOrder,Long> implements CustomerOrderRepository {
    public CustomerOrderRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }
}

