package com.app.repo.generic;


import com.app.model.Stock;
import com.app.repo.CrudRepository;

import java.util.Arrays;
import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Object[]> query4();

    void query1();

    List<Object[]> query5(String name);
}
