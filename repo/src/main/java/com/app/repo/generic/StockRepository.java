package com.app.repo.generic;


import com.app.model.Stock;
import com.app.repo.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Object[]> query4();
}
