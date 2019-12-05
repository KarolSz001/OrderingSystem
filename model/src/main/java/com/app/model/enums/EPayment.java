package com.app.model.enums;

import java.util.Arrays;
import java.util.Random;

public enum EPayment {
    CASH, CARD, MONEY_TRANSFER;

    public void showAllPaymentsMethod(){
        Arrays.toString(values());
    }

    public static EPayment findRandomPayment(){
        return values()[new Random().nextInt(values().length - 1)];
    }
}
