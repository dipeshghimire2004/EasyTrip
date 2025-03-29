package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="guesthouses")
public class Guesthouse {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location; // Could be split into address, city, etc., later

    @Column(nullable = false)
    private String contactDetails;

    private String description;
    private String amenities;   // Could be a JSON or separate table later

    @Column(nullable=false)
    private String verifiedDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuesthouseApprovalStatus status = GuesthouseApprovalStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;
}
