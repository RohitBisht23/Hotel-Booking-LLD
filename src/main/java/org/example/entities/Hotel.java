package org.example.entities;

import org.example.enums.City;

import java.util.List;

public class Hotel {
    private String hotelId;
    private String hotelName;
    private List<Room> totalRooms;
    private String location;
    private City city;


    public Hotel(String hotelName, String location, List<Room> rooms, String hotelId, City city) {
        this.hotelName = hotelName;
        this.location = location;
        this.hotelId = hotelId;
        this.totalRooms = rooms;
        this.city = city;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLocation() {
        return location;
    }

    public List<Room> getAllRooms() {
        return totalRooms;
    }


    public String getHotelId() {
        return hotelId;
    }

    public City getcity() {
        return city;
    }
}
