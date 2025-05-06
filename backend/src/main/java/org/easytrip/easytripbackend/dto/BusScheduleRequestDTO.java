package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

@Data
@Schema(description = "Request DTO for bus schedule creation/update")
public class BusScheduleRequestDTO {
    @NotNull(message = "Bus ID is required")
    @Schema(description = "ID of the bus for scheduling", example = "1")
    private Long busId;

    @NotBlank(message = "Source location is required")
    @Schema(description = "Journey starting point", example = "New York")
    private String source;

    @NotBlank(message = "Destination location is required")
    @Schema(description = "Journey ending point", example = "Boston")
    private String destination;

    @Future(message = "Departure time must be in the future")
    @Schema(description = "Scheduled departure time", example = "2023-12-25T08:00:00")
    private LocalDateTime departureTime;

    @Future(message = "Arrival time must be in the future")
    @Schema(description = "Scheduled arrival time", example = "2023-12-25T12:00:00")
    private LocalDateTime arrivalTime;

    @Positive(message = "Fare must be positive")
    @Schema(description = "Ticket fare amount", example = "150.50")
    private double fare;

    @PositiveOrZero(message = "Available seats cannot be negative")
    @Schema(description = "Number of available seats", example = "40")
    private int availableSeats;
}