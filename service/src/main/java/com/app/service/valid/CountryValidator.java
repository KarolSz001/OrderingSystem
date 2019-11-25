package com.app.service.valid;



import com.app.model.Country;

import java.util.HashMap;
import java.util.Map;

public class CountryValidator extends AbstractValidator<Country>  {

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Z ]+";


    @Override
    public Map<String, String> validate(Country country) {
        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(country.getName())) {
            err.put("Error nr 1", "Name only work with UpCase Letters and SPACE");
        }
        if (!isNameLengthCorrect(country.getName())) {
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
