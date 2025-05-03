package org.easytrip.easytripbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BusScheduleRequestDTO {
    private Long busId;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double fare;
    private int availableSeats;
}

