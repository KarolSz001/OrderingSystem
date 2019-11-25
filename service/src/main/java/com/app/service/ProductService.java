package com.app.service;


import com.app.exception.AppException;
import com.app.model.Category;
import com.app.model.Product;
import com.app.model.enums.GuaranteeComponents;
import com.app.repo.generic.CategoryRepository;
import com.app.repo.generic.ProductRepository;
import com.app.service.valid.ProductValidator;

import java.math.BigDecimal;
import java.util.Set;

public class ProductService {

    ProductValidator productValidator;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;


    public void addProducerToDB(Product product) {

        if (product == null) {
            throw new AppException("object is null");
        }
        productRepository.addOrUpdate(product);
    }

    private boolean isProductAlreadyInDB(Product product) {

        String nameInDB = product.getName();
        Product productInDB = productRepository.findByName(nameInDB).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        String categoryName = product.getCategory().getName();


        return product.getName().equals(productInDB) && product.getCategory().equals(categoryName);
    }


    public Product createProduct() {

        String productName = DataManager.getLine("PRESS PRODUCT NAME");
        BigDecimal price = BigDecimal.valueOf(DataManager.getInt("PRESS PRICE"));
        String categoryName = DataManager.getLine("PRESS CATEGORY NAME");
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new AppException("NO RECORD IN DB"));
        Set<GuaranteeComponents> guaranteeComponents = Set.of(GuaranteeComponents.EXCHANGE, GuaranteeComponents.SERVICE);
        Product product = Product.builder().name(productName).category(category).price(price).category(category).components(guaranteeComponents).build();

        if (isProductAlreadyInDB(product)) {
            throw new AppException("THIS RECORD ISA ALREADY IN DB");
        }

        productValidator.validate(product);
        if (productValidator.hasErrors()) {
            throw new AppException("ERROR IN PRODUCT VALIDATION");
        }

        var insertProduct = productRepository
                .addOrUpdate(product)
                .orElseThrow(() -> new AppException("cannot insert PRODUCT"));

        return insertProduct;
    }


}
