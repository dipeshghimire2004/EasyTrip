package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "Request DTO for bus registration")
public class BusRequestDTO {
    @NotBlank(message = "Bus name is required")
    @Schema(description = "Name of the bus", example = "Volvo AC Sleeper")
    private String name;

    @NotNull(message = "Bus type is required")
    @Schema(description = "Type of the bus", example = "AC_SLEEPER")
    private BusType busType;

    @Positive(message = "Total seats must be positive")
    @Schema(description = "Total number of seats in the bus", example = "40")
    private int totalSeats;

    @Schema(description = "Verified document image file")
    private MultipartFile verifiedDocumentImage;
}