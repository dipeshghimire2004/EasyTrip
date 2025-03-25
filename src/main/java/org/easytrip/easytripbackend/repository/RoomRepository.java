package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByGuestHouseId(int guestHouseId);
    List<Room> findByGuestIdAndIsAvailableTrue(Long guestId);
}
