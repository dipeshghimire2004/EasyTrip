package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.easytrip.easytripbackend.model.User;

@Data
@Schema(description = "Response body for guesthouse registration")
@NoArgsConstructor
@AllArgsConstructor
public class GuesthouseResponseDTO {
    private long id;
    private String name;
    private String location;
    private String contactDetails;
    private String description;
    private String amenities;
    private String verifiedDocument;
    private String status;
    private double pricePerNight;
//    private User owner;
}
