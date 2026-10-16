package org.example.repositories;

import org.example.entities.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingRepository {
    Map<String, Booking> bookingMap = new HashMap<>();

    public void save(Booking booking) {
        bookingMap.put(booking.getBookingId(), booking);
    }

    public Booking getBookingByBookingId(String bookingId) {
        return bookingMap.get(bookingId);
    }
}
