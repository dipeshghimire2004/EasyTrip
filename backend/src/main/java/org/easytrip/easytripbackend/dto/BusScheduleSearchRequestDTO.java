package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;
@Data
@Schema(description = "Request DTO for searching bus schedules")
public class BusScheduleSearchRequestDTO {
    @NotBlank(message = "Source location is required")
    @Schema(description = "Journey source location", example = "New York")
    private String source;

    @NotBlank(message = "Destination location is required")
    @Schema(description = "Journey destination location", example = "Boston")
    private String destination;

    @Schema(description = "Date of travel (yyyy-MM-dd format)", example = "2023-12-25")
    private String travelDate;

    @Schema(description = "Preferred bus type", example = "AC_SLEEPER")
    private BusType busType;

    @Schema(description = "Minimum fare filter", example = "100.0")
    private Double minFare;

    @Schema(description = "Maximum fare filter", example = "500.0")
    private Double maxFare;
}