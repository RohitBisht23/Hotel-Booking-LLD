package org.example.services;

import org.example.entities.Booking;
import org.example.entities.Hotel;
import org.example.entities.Room;
import org.example.enums.BookingStatus;
import org.example.enums.PaymentType;
import org.example.repositories.BookingRepository;
import org.example.strategy.Payment.Factory;
import org.example.strategy.Payment.PaymentStrategy;
import org.example.strategy.lock.LockProvider;

import java.util.List;
import java.util.UUID;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final LockProvider lockProvider;

    private static final long TTL = 5000; //5 sec

    public BookingService(BookingRepository bookingRepository, LockProvider lockProvider) {
        this.bookingRepository = bookingRepository;
        this.lockProvider = lockProvider;
    }

    public Booking createBooking(String userId, Hotel hotel, List<String> roomIds) {
        System.out.println(hotel.getAllRooms());
        //Lock all rooms and check available
        for (String roomNumber : roomIds) {
            String key = hotel.getHotelId()+":"+roomNumber;
            if(!lockProvider.tryLock(key, userId, TTL)) {
                throw new IllegalArgumentException("Unable to lock the room");
            }
        }

        double totalPrice = 0.0;
        for(Room room : hotel.getAllRooms()) {
            if(roomIds.contains(room.getRoomNumber())) {
                totalPrice += room.getPrice();
            }
        }

        Booking newBooking = new Booking(
                UUID.randomUUID(),
                userId,
                roomIds,
                BookingStatus.CREATED,
                hotel,
                totalPrice
        );

        bookingRepository.save(newBooking);
        System.out.println("New booking is created by user :"+newBooking.getBookedBy());

        return newBooking;
    }

    public void confirmBooking(Booking booking, PaymentType paymentType) {
        if(booking.getBookingStatus() != BookingStatus.CREATED) {
            throw new IllegalStateException("Booking is not in valid state for confirming.");
        }
        for(String roomNumber : booking.getRoomNumbers()) {
            String key = booking.getHotel().getHotelId() + ":" + roomNumber;
            if(lockProvider.isLockExpired(key) || !lockProvider.isLockedBy(key, booking.getBookedBy())) {
                throw new IllegalArgumentException("Unable to lock the room");
            }
        }

        booking.setPayment(paymentType);
        PaymentStrategy paymentStrategy = Factory.createObjectByPaymentType(paymentType);
        paymentStrategy.pay(booking.getAmount());

        for(String roomNumber : booking.getRoomNumbers()) {
            String key = booking.getBookedBy()+":"+roomNumber;
            lockProvider.unlock(key);
        }

        booking.setBookingStatus(BookingStatus.CONFIRM);
        System.out.println("Booking is confirmed :"+booking.getBookingId()+ " by user :"+booking.getBookedBy());
    }
}
