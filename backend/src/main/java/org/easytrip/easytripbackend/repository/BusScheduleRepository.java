package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.BusSchedule;
import org.easytrip.easytripbackend.model.BusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {
//    @Query("SELECT s FROM BusSchedule s JOIN s.bus b WHERE s.source = :source AND s.destination = :destination " +
//            "AND s.departureTime >= :startOfDay AND s.departureTime < :endOfDay " +
//            "AND b.status = :status " +
//            "AND (:busType IS NULL OR b.busType = :busType) " +
//            "AND (:minFare IS NULL OR s.fare >= :minFare) " +
//            "AND (:maxFare IS NULL OR s.fare <= :maxFare)")
//    List<BusSchedule> findBySearchCriteria(
//            @Param("source") String source,
//            @Param("destination") String destination,
//            @Param("startOfDay") LocalDateTime startOfDay,
//            @Param("endOfDay") LocalDateTime endOfDay,
//            @Param("status") ApprovalStatus status,
//            @Param("busType") BusType busType,
//            @Param("minFare") Double minFare,
//            @Param("maxFare") Double maxFare);
//@Query("SELECT s FROM BusSchedule s JOIN s.bus b WHERE s.source = :source")
//List<BusSchedule> findBySearchCriteria(@Param("source") String source);
}
