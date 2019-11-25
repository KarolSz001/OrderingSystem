package com.app.service.valid;

import com.app.model.Trade;

import java.util.HashMap;
import java.util.Map;

public class TradeValidator extends AbstractValidator<Trade>{

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Z ]+";

    @Override
    public Map<String, String> validate(Trade trade) {
        Map<String, String> err = new HashMap<>();

        if (!isNameCorrect(trade.getName())) {
            err.put("Error nr 1", "Name only work with UpCase Letters and SPACE");
        }
        if (!isNameLengthCorrect(trade.getName())) {
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
