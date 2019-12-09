package com.app.model.dto;

import com.app.model.Product;

public interface Mapper {

    static ProductDTO fromProductToProductDTO(Product product){
        return product == null ? null : ProductDTO.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .producerName(product.getProducer().getName())
                .countryName(product.getProducer().getCountry().getName())
                .build();
    }

    static ProductWithGuarDTO fromProductToProductWithGuarDTO(Product product){
        return product == null ? null : ProductWithGuarDTO.builder()
                .productName(product.getName())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .producerName(product.getProducer().getName())
                .countryName(product.getProducer().getCountry().getName())
                .guaranteeComponents(product.getComponents())
                .build();
    }
}
