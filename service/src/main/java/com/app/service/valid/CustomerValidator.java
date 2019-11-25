package com.app.service.valid;



import com.app.model.Customer;


import java.util.HashMap;
import java.util.Map;

public class CustomerValidator extends AbstractValidator<Customer> {
    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";
    private final String SURNAME_REGEX = "[A-Za-z]+";

    @Override

    public Map<String, String> validate(Customer customer) {

        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(customer.getName())) {
            err.put("Error nr 1", "Name only work with Letters");
        }
        if (!isSurnameCorrect(customer.getSurname())) {
            err.put("Error nr 2", "Surname only work with Letters");
        }
        if (!isNameLengthCorrect(customer.getName())) {
            err.put("Error nr 3", "Name is too short");
        }

        if (!isSurnameLengthCorrect(customer.getSurname())) {
            err.put("Error nr 4", "Surname is too short");
        }
        if (!isAgeCorrect(customer.getAge())) {
            err.put("Error nr 5", "Age is wrong");
        }

        return err;
    }

    private boolean isNameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }

    private boolean isSurnameCorrect(String name) {
        return name.matches(SURNAME_REGEX);
    }

    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }

    private boolean isSurnameLengthCorrect(String surname) {
        return surname.length() > MIN_LENGTH_NAME;
    }

    private boolean isAgeCorrect(Integer age) {
        return age >= 18;
    }


}
