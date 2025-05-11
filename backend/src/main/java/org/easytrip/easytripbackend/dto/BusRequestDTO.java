package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

import java.time.LocalDateTime;


import java.time.LocalDateTime;

@Data
public class BusRequestDTO {
    @NotBlank
    @Schema(description = "Name of the bus", example = "City Express")
    private String name;

    @NotBlank
    @Schema(description = "Name of the bus owner", example = "John Doe")
    private String ownerName;

    @NotBlank
    @Schema(description = "Phone number of the bus owner", example = "1234567890")
    private String ownerPhone;

    @NotNull
    @Schema(description = "Type of bus (AC, NON_AC, SLEEPER)", example = "AC")
    private BusType busType;

    @NotNull
    @Schema(description = "Total number of seats", example = "40")
    private Integer totalSeats;

    @NotBlank
    @Schema(description = "Starting location", example = "New York")
    private String source;

    @NotBlank
    @Schema(description = "Destination location", example = "Boston")
    private String destination;

    @NotNull
    @Schema(description = "Departure date and time", example = "2025-05-12T10:00:00")
    private LocalDateTime departureTime;

    @NotNull
    @Schema(description = "Arrival date and time", example = "2025-05-12T14:00:00")
    private LocalDateTime arrivalTime;

    @NotNull
    @Schema(description = "Fare per seat in USD", example = "50.00")
    private Double farePerSeat;

    @NotNull
    @Schema(description = "Verification document (PDF/image)", type = "string", format = "binary")
    private MultipartFile verifiedDocument;
}