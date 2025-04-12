package org.easytrip.easytripbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private String paymentOption = "Cash on Arrival"; // Default for Sprint 1

    @Column(nullable = false)
    private String status ="CONFIRMED";// e.g., "CONFIRMED", "CANCELLED","MODIFIED"
}
