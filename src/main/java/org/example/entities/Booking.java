package org.example.entities;

import org.example.enums.BookingStatus;
import org.example.enums.PaymentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Booking {
    private String bookingId;
    private String bookedBy;
    private LocalDateTime checkInTime;
    private LocalDateTime checkoutTime;
    private List<String> roomNumbers;
    private Hotel hotel;
    private BookingStatus bookingStatus;
    private double amount;
    private PaymentType paymentType;

    public Booking(UUID bookingId, String bookedBy, List<String> roomNumbers, BookingStatus bookingStatus, Hotel hotel, double price) {
        this.bookedBy = bookedBy;
        this.bookingId = String.valueOf(bookingId);
        this.roomNumbers = roomNumbers;
        this.bookingStatus = bookingStatus;
        this.checkInTime = LocalDateTime.now();
        this.checkoutTime = null;
        this.hotel = hotel;
        this.amount = price;
    }

    public void setCheckoutTime(LocalDateTime checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckoutTime() {
        return checkoutTime;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public List<String> getRoomNumbers() {
        return roomNumbers;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setPayment(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
