package org.easytrip.easytripbackend.dto;

import lombok.Data;

@Data
public class RoomResponseDTO {
    private Long id;
//    private Long guesthouseId;  //optional
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable=true;
    private int capacity;
}
