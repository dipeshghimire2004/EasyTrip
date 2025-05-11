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
        @JoinColumn(name = "client_id", nullable = false)
        private User client;

        @ManyToOne
        @JoinColumn(name = "bus_id", nullable = false)
        private Bus bus;

        @Column(nullable = false)
        private LocalDateTime bookingTime;

        @Column(nullable = false)
        private int seatsBooked;

        @Column(nullable = false)
        private boolean isCancelled = false;
    }