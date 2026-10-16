package org.example;

import org.example.entities.*;
import org.example.enums.City;
import org.example.enums.PaymentType;
import org.example.enums.RoomType;
import org.example.repositories.BookingRepository;
import org.example.repositories.HotelRepository;
import org.example.services.BookingService;
import org.example.strategy.lock.InMemoryLockProvider;
import org.example.strategy.lock.LockProvider;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        BookingRepository bookingRepository = new BookingRepository();
        HotelRepository hotelRepository = new HotelRepository();

        //Lock
        LockProvider lockProvider = new InMemoryLockProvider();

        BookingService bookingService = new BookingService(bookingRepository, lockProvider);

        //Luxury room
        Room luxuryRoom1 = new LuxuryRoom(1500, RoomType.LUXURY, "101");
        Room luxuryRoom2 = new LuxuryRoom(1500, RoomType.LUXURY, "102");
        Room luxuryRoom5 = new LuxuryRoom(1500, RoomType.LUXURY, "105");
        Room luxuryRoom6 = new LuxuryRoom(1500, RoomType.LUXURY, "106");

        //Ultra Luxury
        Room ultraLuxury = new UltraLuxuryRoom(2500, RoomType.ULTRA_LUXURY, "103");

        //Hotel
        Hotel hotel = new Hotel("Shyam-baba-hotel", "Halwani", List.of(luxuryRoom1, luxuryRoom2, luxuryRoom5, luxuryRoom6, ultraLuxury), "Hotel1", City.HALDWANI);
        Booking newBooking = bookingService.createBooking("Rohit1", hotel, List.of("101","102"));

        bookingService.confirmBooking(newBooking, PaymentType.CART);


        //Demo 2-> Creating booking when two user have chosen same rooms at the same time
        System.out.println("\n\n\n Demo 2 Two user have selected same rooms to book at same time");
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService
                .submit(() -> {
                    try {
                        Booking booking1 = bookingService.createBooking("Ram", hotel, List.of("105","106"));
                        Thread.sleep(1000);
                        bookingService.confirmBooking(booking1, PaymentType.UPI);
                    } catch (Exception e) {
                        System.out.println("Failed to book by user Ram due to :"+e.getMessage());
                    }
                });

        executorService
                .submit(() -> {
                    try {
                        Booking booking2 = bookingService.createBooking("Shyam", hotel,List.of("105","106"));
                        Thread.sleep(1000);
                        bookingService.confirmBooking(booking2, PaymentType.UPI);
                    } catch (Exception e) {
                        System.out.println("Failed to book by user Shyam due to :"+e.getMessage());
                    }
                });

        //Demo 3 -> When lock is expired before the payment done

        System.out.println("\n\n\n\nDemo 3 When LLT is expired before the payment done");

        try {
            System.out.println("Creating booking before the payment for user 1");
            Booking b1 = bookingService.createBooking("AA", hotel, List.of("101","102"));
            System.out.println("Booking created for user AA");
            Thread.sleep(6000); //6 sec

            System.out.println("Creating booking before the payment for user 2");
            Booking b2 = bookingService.createBooking("BB", hotel, List.of("101", "102"));
            System.out.println("Booking created for user BB");

            try {
                System.out.println("AA user trying to pay for the booking and mark created booking to confirm");
                bookingService.confirmBooking(b1, PaymentType.UPI);
            } catch (Exception e) {
                System.out.println("Failed to pay :"+e.getMessage());
            }

            try {
                System.out.println("BB user trying to pay for the booking and mark created booking to confirm");
                bookingService.confirmBooking(b2, PaymentType.UPI);
            } catch (Exception e) {
                System.out.println("Failed to pay :"+e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("user failed :"+e.getMessage());
        }
    }
}