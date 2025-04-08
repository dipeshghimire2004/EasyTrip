package org.easytrip.easytripbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.easytrip.easytripbackend.model.User;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private Long guesthouseId;
    private User traveler;
    private String guesthouseName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private String paymentOption; // e.g., "Cash on Arrival"
    private String status;
}
