package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTravelerId(Long travelerId);
    List<Booking> findByGuesthouseId(Long guesthouseId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.traveler JOIN FETCH b.guesthouse JOIN FETCH b.room where b.id= :id")
    Optional<Booking> findIdWithDetails(@Param("id") Long id);
}
