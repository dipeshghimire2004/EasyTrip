package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.easytrip.easytripbackend.model.PaymentMethod;

import java.time.LocalDateTime;

@Data
public class BusBookingResponseDTO {
    @Schema(description = "ID of the booking", example = "1")
    private Long id;

    @Schema(description = "ID of the client", example = "2")
    private Long clientId;

    @Schema(description = "ID of the bus", example = "1")
    private Long busId;

    @Schema(description = "Booking date and time", example = "2025-05-11T12:00:00")
    private LocalDateTime bookingTime;

    @Schema(description = "Number of seats booked", example = "2")
    private Integer seatsBooked;

    @Schema(description = "Whether the booking is cancelled", example = "false")
    private boolean isCancelled;

    @Schema(description = "Payment method (CASH_ON_ARRIVAL)", example = "CASH_ON_ARRIVAL")
    private PaymentMethod paymentMethod;
}