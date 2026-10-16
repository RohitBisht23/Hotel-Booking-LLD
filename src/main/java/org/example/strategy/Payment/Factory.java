package org.example.strategy.Payment;

import org.example.enums.PaymentType;

public class Factory {
    public static PaymentStrategy createObjectByPaymentType(PaymentType paymentType) {
        if(paymentType == PaymentType.CART) {
            return new CardPayment();
        } else if(paymentType == PaymentType.CASH) {
            return new CashPayment();
        } else if(paymentType == PaymentType.UPI) {
            return new CashPayment();
        } else {
            return null;
        }
    }
}
