package com.app.model.enums;

import java.util.Random;

public enum GuaranteeComponents {

    HELP_DESK, MONEY_BACK, SERVICE, EXCHANGE;


    public static  GuaranteeComponents getRandomComponent() {
        int size = GuaranteeComponents.values().length;
        return values()[new Random().nextInt(size - 1)];
    }
}
