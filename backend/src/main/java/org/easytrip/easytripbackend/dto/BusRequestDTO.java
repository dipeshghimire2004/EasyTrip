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
@Schema(name = "BusRequestDTO", description = "DTO for registering or updating a bus")
public class BusRequestDTO {

    @NotBlank
    @Schema(description = "Name of the bus", example = "Mountain Express")
    private String name;

    @NotBlank
    @Schema(description = "Owner's full name", example = "Dipesh Ghimire")
    private String ownerName;

    @NotBlank
    @Schema(description = "Owner's phone number", example = "+9779800000000")
    private String ownerPhone;

    @Schema(description = "Type of bus (e.g. LUXURY, STANDARD)", example = "LUXURY")
    private BusType busType;

    @Schema(description = "Total number of seats in the bus", example = "45")
    private int totalSeats;

    @NotBlank
    @Schema(description = "Source location", example = "Kathmandu")
    private String source;

    @NotBlank
    @Schema(description = "Destination location", example = "Pokhara")
    private String destination;

    @Schema(description = "Scheduled departure time (ISO format)", example = "2025-06-01T08:00:00")
    private LocalDateTime departureTime;

    @Schema(description = "Expected arrival time (ISO format)", example = "2025-06-01T14:00:00")
    private LocalDateTime arrivalTime;

    @NotBlank
    @Schema(description = "Verification document (PDF/image)", type = "string", format = "binary")
    private MultipartFile verifiedDocument;

    @Schema(description = "Additional description or notes about the bus", example = "Comfortable AC coach with WiFi")
    private String description;
}