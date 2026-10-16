package org.example.strategy.Payment;

public class CashPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("Cash received amount :"+amount);
    }
}
