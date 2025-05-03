package org.easytrip.easytripbackend.dto;

import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;

import java.time.LocalDateTime;

@Data
public class BusScheduleSearchResponseDTO {
    private Long busScheduleId;
    private String busName;
    private BusType busType;
    private int totalSeats;
    private int availableSeats;
    private LocalDateTime departureTime;
    private double fare;
}
