package com.app.service.valid;


import com.app.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator extends AbstractValidator<Product>{

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";

    @Override
    public Map<String, String> validate(Product product) {

        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(product.getName())) {
            err.put("Error nr 1", "Name only work with Letters");
        }
        if (!isNameLengthCorrect(product.getName())) {
            err.put("Error nr 2", "Name is too short");
        }
        if (!isPriceCorrect(product.getPrice())) {
            err.put("Error nr 3", "Price has negative value");
        }
        return err;
    }

    private boolean isNameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }

    private boolean isPriceCorrect(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }
}
