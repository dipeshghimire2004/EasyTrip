package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.easytrip.easytripbackend.model.User;

import java.util.List;

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
    private List<String> amenities;
    private String verifiedDocument;
    private Long ownerId;
    private String ownerName;
    private String status;
    private double pricePerNight;
//    private User owner;
}
