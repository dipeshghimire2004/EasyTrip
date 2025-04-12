package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for guesthouse registration")
public class GuesthouseRequestDTO {
    @NotBlank
    @Schema(description = "Name of the guesthouse", example = "Cozy Haven")
    private String name;

    @NotBlank
    @Schema(description = "Location of the guesthouse", example = "123 Main St, Kathmandu")
    private String location;

    @NotBlank
    @Schema(description = "Contact details (e.g., phone or email)", example = "owner@example.com")
    private String contactDetails;

    @NotBlank
    @Schema(description = "Verification document (PDF/image)", type = "string", format = "binary")
    private MultipartFile verifiedDocument;

//    @NotBlank
//    private Long ownerId;
    private String description;
    private String amenities;
    private double pricePerNight;
    private String status;

}
