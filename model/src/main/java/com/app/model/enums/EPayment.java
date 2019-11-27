package com.app.model.enums;

import java.util.Arrays;

public enum EPayment {
    CASH, CARD, MONEY_TRANSFER;

    public void showAllPaymentsMethod(){
        Arrays.toString(values());
    }
}
