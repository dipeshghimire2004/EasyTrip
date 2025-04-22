package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByGuesthouseId(Long guesthouseId);
//    List<Room> findByIsAvailableTrue();
    boolean existsByGuesthouseIdAndRoomNumber(Long guestHouseId, String roomNumber);
    boolean findByGuesthouseIdAndIsAvailableTrue(Long guestHouseId);
}
