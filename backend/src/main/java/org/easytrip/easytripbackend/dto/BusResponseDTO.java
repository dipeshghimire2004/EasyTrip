package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.BusType;

import lombok.Data;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
@Data
public class BusResponseDTO {
    @Schema(description = "ID of the bus", example = "1")
    private Long id;

    @Schema(description = "Name of the bus", example = "City Express")
    private String name;

    @Schema(description = "Name of the bus owner", example = "John Doe")
    private String ownerName;

    @Schema(description = "Phone number of the bus owner", example = "1234567890")
    private String ownerPhone;

    @Schema(description = "Type of bus (AC, NON_AC, SLEEPER)", example = "AC")
    private BusType busType;

    @Schema(description = "Total number of seats", example = "40")
    private Integer totalSeats;

    @Schema(description = "Starting location", example = "New York")
    private String source;

    @Schema(description = "Destination location", example = "Boston")
    private String destination;

    @Schema(description = "Departure date and time", example = "2025-05-12T10:00:00")
    private LocalDateTime departureTime;

    @Schema(description = "Arrival date and time", example = "2025-05-12T14:00:00")
    private LocalDateTime arrivalTime;

    @Schema(description = "Path to verification document", example = "upload/1_1623456789_bus1.jpg")
    private String verifiedDocumentImage;

    @Schema(description = "Fare per seat in USD", example = "50.00")
    private Double farePerSeat;

    @Schema(description = "Approval status (PENDING, APPROVED, DENIED)", example = "APPROVED")
    private ApprovalStatus status;

    @Schema(description = "ID of the bus operator", example = "1")
    private Long operatorId;
}