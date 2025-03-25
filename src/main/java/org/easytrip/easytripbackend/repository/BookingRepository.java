package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTravalerId(Long travalerId);
    List<Booking> findByRoomGuestHouseId(Long bookingId);
}
