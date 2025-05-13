package org.easytrip.easytripbackend.repository;

import jakarta.persistence.LockModeType;
import org.easytrip.easytripbackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.guesthouseId = :guesthouseId")
    List<Room> findByGuesthouseId(@Param("guesthouseId") Long guesthouseId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r WHERE r.guesthouseId = :guesthouseId AND r.roomNumber = :roomNumber")
    boolean existsByGuesthouseIdAndRoomNumber(@Param("guesthouseId") Long guesthouseId, @Param("roomNumber") String roomNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Room r WHERE r.id = :id")
    Optional<Room> findById(Long id, LockModeType lockMode);
//    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r WHERE r.guesthouseId = :guesthouseId AND r.isAvailable = true")
//    boolean existsByGuesthouseIdAndIsAvailableTrue(@Param("guesthouseId") Long guesthouseId);
}
