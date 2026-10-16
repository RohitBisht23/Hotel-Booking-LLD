package org.example.strategy.Payment;

public class UPIPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println("UPI payment amount :"+amount);
    }
}
