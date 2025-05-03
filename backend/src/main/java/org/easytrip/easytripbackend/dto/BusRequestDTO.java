package org.easytrip.easytripbackend.dto;

import lombok.Data;
import org.easytrip.easytripbackend.model.BusType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BusRequestDTO {
    private String name;
    private BusType busType;
    private int totalSeats;
    private MultipartFile verifiedDocumentImage;
}