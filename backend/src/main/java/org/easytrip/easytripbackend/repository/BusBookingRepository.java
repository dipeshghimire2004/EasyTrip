package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {
//    BusBooking findById(long id);
//    List<BusBooking> findByClientId(Long clientId);
    List<BusBooking> findByClientIdAndIsCancelledFalse(Long clientId);
}
