package com.app.service.valid;

import com.app.model.Category;
import com.app.model.Country;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidator extends AbstractValidator<Category>{

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Z]+";


    @Override
    public Map<String, String> validate(Category category) {
        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(category.getName())) {
            err.put("Error nr 1", "Name only work with UpCase Letters");
        }
        if (!isNameLengthCorrect(category.getName())) {
            err.put("Error nr 3", "Name is too short");
        }

        return err;
    }

    private boolean isNameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }


    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }

}
