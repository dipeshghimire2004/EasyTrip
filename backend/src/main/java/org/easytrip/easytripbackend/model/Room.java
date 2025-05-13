package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="guesthouse_id", nullable=false)
    private Long guesthouseId;

//    @ManyToOne
//    @JoinColumn(name="guesthouse_id", nullable=false)
//    private Guesthouse guestHouse;

    @NotBlank(message="Room number is required")
    @Column(unique=true)
    private String roomNumber;

    @Column(nullable=false)
    private String roomType;    //e.g. Single, Double


    @Column(nullable =false)
    @Positive(message="price must be positive")
    private double pricePerNight;

    @Column(nullable=false)
    private boolean isAvailable=true;

    @Positive(message="Capacity must be positive")
    private int capacity;

    @Version
    @Column(nullable=false)
    private Long version;
}
