package org.easytrip.easytripbackend.dto;

import lombok.Data;


@Data
public class BusBookingRequestDTO {
    private Long busId;
    private int seatsBooked;
}
