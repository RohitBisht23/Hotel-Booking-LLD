package org.example.services;

import org.example.entities.Hotel;
import org.example.repositories.HotelRepository;

import java.util.List;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotelsByCity(String city) {
        List<Hotel> hotels = hotelRepository.getHotelByCity(city);
        return hotels;
    }

}
