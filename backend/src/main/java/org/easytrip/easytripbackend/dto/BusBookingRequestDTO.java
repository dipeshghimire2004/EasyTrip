package org.easytrip.easytripbackend.dto;

import lombok.Data;

@Data
public class BusBookingRequestDTO {
    private Long scheduleId;
    private int numberOfSeats;
}
