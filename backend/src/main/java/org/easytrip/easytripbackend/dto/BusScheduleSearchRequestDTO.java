package org.easytrip.easytripbackend.dto;

import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;

@Data
public class BusScheduleSearchRequestDTO {
    private String source;
    private String destination;
    private String travelDate;
    private BusType busType;
    private Double minFare;
    private Double maxFare;
}
