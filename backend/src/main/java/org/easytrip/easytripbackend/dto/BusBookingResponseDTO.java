package org.easytrip.easytripbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BusBookingResponseDTO {
    private Long id;
    private Long scheduleId;
    private Long clientId;
    private int numberOfSeats;
    private double totalFare;
    private LocalDateTime bookingTime;
    private boolean isCancelled;
}