package org.easytrip.easytripbackend.dto;

import lombok.Data;
import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.BusType;

import lombok.Data;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Data
public class BusResponseDTO {
    private Long id;
    private String name;
    private String ownerName;
    private String ownerPhone;
    private BusType busType;
    private int totalSeats;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String verifiedDocumentImage;
    private String description;
    private ApprovalStatus status;
    private Long operatorId;
}