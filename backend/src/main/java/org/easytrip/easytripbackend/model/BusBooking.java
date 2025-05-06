package org.easytrip.easytripbackend.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class BusBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private BusSchedule schedule;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    private int numberOfSeats;
    private double totalFare;
    private LocalDateTime bookingTime;
    private boolean isCancelled;
}