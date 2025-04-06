package org.easytrip.easytripbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User traveler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="guesthouse_id", nullable=false)
    private Guesthouse guesthouse;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private String status; // e.g., "CONFIRMED", "CANCELLED"

    @Column(nullable = false)
    private double totalPrice;



//    @ManyToOne
//    @JoinColumn(name="room_id", nullable=false)
//    private Room room;
//
//    @Column(nullable=false)
//    private LocalDateTime checkOutTime;
}
