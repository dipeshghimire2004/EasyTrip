package org.easytrip.easytripbackend.repository;

import ch.qos.logback.core.status.Status;
import org.easytrip.easytripbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findByTravelerId(Long travelerId);
//    List<Booking> findByGuesthouseId(Long guesthouseId);

//    @Query("SELECT b FROM Booking b JOIN FETCH b.traveler JOIN FETCH b.guesthouse JOIN FETCH b.room where b.id= :id")
//    Optional<Booking> findIdWithDetails(@Param("id") Long id);

    @Query("SELECT b FROM Booking b WhERE b.status =:status AND b.checkOutDate = :checkOutDate")
    List<Booking> findByStatusAndCheckOutDateBefore(String status, LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.status = 'CONFIRMED' " +
            "AND (b.checkInDate <= :checkOutDate AND b.checkOutDate > :checkInDate)")
    List<Booking> findOverlappingBookings(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}
