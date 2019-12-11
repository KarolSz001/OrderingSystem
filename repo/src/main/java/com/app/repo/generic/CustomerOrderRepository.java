package com.app.repo.generic;


import com.app.model.CustomerOrder;
import com.app.repo.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder,Long> {
    List<CustomerOrder> query6(LocalDate min, LocalDate max);
    List<CustomerOrder> query7(String surname, String name);
    List<CustomerOrder> query8a();
    }
