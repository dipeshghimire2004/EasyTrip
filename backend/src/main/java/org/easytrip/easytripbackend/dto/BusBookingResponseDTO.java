package org.easytrip.easytripbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BusBookingResponseDTO {
    private Long id;
    private Long clientId;
    private Long busId;
    private LocalDateTime bookingTime;
    private int seatsBooked;
    private boolean isCancelled;
}