package org.easytrip.easytripbackend.dto;

import lombok.Data;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.BusType;

@Data
public class BusResponseDTO {
    private Long id;
    private String name;
    private BusType busType;
    private int totalSeats;
    private String verifiedDocumentImage;
    private ApprovalStatus status;
    private Long operatorId;
}