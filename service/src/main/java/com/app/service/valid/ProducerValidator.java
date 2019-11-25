package com.app.service.valid;



import com.app.model.Producer;

import java.util.HashMap;
import java.util.Map;

public class ProducerValidator extends AbstractValidator<Producer> {


    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";

    @Override
    public Map<String, String> validate(Producer producer) {

        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(producer.getName())) {
            err.put("Error nr 1", "Name only work with Letters");
        }
        if (!isNameLengthCorrect(producer.getName())) {
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
