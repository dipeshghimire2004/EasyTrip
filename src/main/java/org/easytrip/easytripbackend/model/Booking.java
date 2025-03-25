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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User traveller;

    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private Room room;

    @Column(nullable=false)
    private LocalDateTime checkOutTime;



}
