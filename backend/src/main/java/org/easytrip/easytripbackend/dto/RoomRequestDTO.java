package org.easytrip.easytripbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data

public class RoomRequestDTO {
    @NotBlank(message="Room number is required")
    private String roomNumber;

    @NotBlank(message="room type is required")
    private String roomType;

    @Positive(message="price must be positive")
    private int pricePerNight;

    private boolean isAvailable;

    @Positive(message="capacity must be positive")
    private int capacity;
}
