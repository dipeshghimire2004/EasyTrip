package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "guesthouse_amenities", joinColumns = @JoinColumn(name = "gueshouse_id"))
    @Column(name = "amenity")
    private Set<String> amenities;

    @Column(nullable=false)
    private String verifiedDocumentImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

}
