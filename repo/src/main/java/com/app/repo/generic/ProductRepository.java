package com.app.repo.generic;

import com.app.model.Product;
import com.app.repo.CrudRepository;


import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Integer> getQuantityOfProductInStock(String productName);
    Optional<Long> getIdProductInStock(String productName);


}
