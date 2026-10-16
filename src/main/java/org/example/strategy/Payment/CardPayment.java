package org.example.strategy.Payment;

public class CardPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("Paid through card amount is :"+amount);
    }
}
