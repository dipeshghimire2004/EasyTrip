package org.easytrip.easytripbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate; // Booking start date/time

    @Column(nullable = false)
    private LocalDateTime endDate; // Booking end date/time

    @Column(nullable = false)
    private String status; // e.g., "CONFIRMED", "CANCELLED"

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gueshhouse_id", nullable=false)
    private Guesthouse guesthouse;

//    @ManyToOne
//    @JoinColumn(name="room_id", nullable=false)
//    private Room room;
//
//    @Column(nullable=false)
//    private LocalDateTime checkOutTime;
}
