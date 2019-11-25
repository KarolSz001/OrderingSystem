package com.app.repo.impl;

import com.app.model.Stock;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.StockRepository;


public class StockRepositoryImpl extends AbstractCrudRepository<Stock,Long> implements StockRepository {
    public StockRepositoryImpl(String persistenceUnit) {
        super(persistenceUnit);
    }
}
