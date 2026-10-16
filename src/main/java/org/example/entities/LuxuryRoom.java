package org.example.entities;

import org.example.enums.RoomType;

import java.util.List;

public class LuxuryRoom extends Room{

    public LuxuryRoom(double price, RoomType roomType, String roomNumber) {
        super(price, roomType, roomNumber);
    }
}
