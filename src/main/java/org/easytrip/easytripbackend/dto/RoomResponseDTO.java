package org.easytrip.easytripbackend.dto;

import lombok.Data;

@Data
public class RoomResponseDTO {
    private Long id;
    private Long guesthouseId;
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;
    private int capacity;
}
