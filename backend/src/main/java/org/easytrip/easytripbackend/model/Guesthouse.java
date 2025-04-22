package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @ElementCollection
    @CollectionTable(name="guesthouse_amenities", joinColumns=@JoinColumn(name="gueshouse_id"))
    @Column(name="amenity")
    private List<String> amenities;

    @Column(nullable=false)
    private String verifiedDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuesthouseApprovalStatus status = GuesthouseApprovalStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;


//    private double pricePerNight;
}
