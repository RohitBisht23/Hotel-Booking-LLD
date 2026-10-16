package org.example.repositories;

import org.example.entities.Hotel;
import org.example.entities.Room;
import org.example.enums.RoomStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HotelRepository {
    //Hotel id and hotel
    private Map<String, Hotel> hotelMap = new HashMap<>();

    public void save(Hotel hotel) {
        hotelMap.put(hotel.getHotelId(), hotel);
    }


    //Considering hotels are having unique name
    public Hotel getHotelByName(String hotelName) {
        return hotelMap.values().stream()
                .filter(key-> key.getHotelName().equalsIgnoreCase(hotelName))
                .findFirst()
                .orElse(null);
    }

    public List<Room> getAvailableRoomByHotelName(String hotelName) {
        Hotel hotel =  hotelMap.values().stream()
                .filter(key -> key.getHotelName().equalsIgnoreCase(hotelName))
                .findFirst()
                .orElse(null);
        return
                hotel.getAllRooms().stream().filter(k -> k.getRoomStatus() == RoomStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Hotel> getHotelByCity(String city) {
        return  hotelMap.values().stream()
                .filter(key -> key.getcity().name().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }
}
