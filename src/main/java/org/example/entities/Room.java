package org.example.entities;

import org.example.enums.RoomStatus;
import org.example.enums.RoomType;

public abstract class Room {
    private final double price;
    private final RoomType roomType;
    private final String roomNumber;
    private  RoomStatus roomStatus;

    public Room(double price, RoomType roomType, String roomNumber) {
        this.price = price;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void SetHotelStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
