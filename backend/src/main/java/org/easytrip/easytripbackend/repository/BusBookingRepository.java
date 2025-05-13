package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {
//    BusBooking findById(long id);
//    List<BusBooking> findByClientId(Long clientId);
    List<BusBooking> findByClientIdAndIsCancelledFalse(Long clientId);


    @Query("SELECT bb FROM BusBooking bb WHERE bb.bus.id IN :busIds")
    List<BusBooking> findByBusIds(@Param("busIds") List<Long> busIds);
}
