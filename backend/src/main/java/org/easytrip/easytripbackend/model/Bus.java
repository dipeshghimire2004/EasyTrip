package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    private int totalSeats;

    private String verifiedDocumentImage;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private BusOperator operator;

}
