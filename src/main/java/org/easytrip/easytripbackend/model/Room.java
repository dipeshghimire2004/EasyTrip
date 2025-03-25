package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="guesthouse_id", nullable=false)
    private Guesthouse guestHouse;

    @Column(nullable=false)
    private String roomType;    //e.g. Single, Double

    @Column(nullable =false)
    private double pricePerNight;

    @Column(nullable=false)
    private boolean isAvailable=true;
}
