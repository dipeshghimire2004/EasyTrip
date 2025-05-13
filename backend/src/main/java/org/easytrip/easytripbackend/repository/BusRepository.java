package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.ApprovalStatus;
import org.easytrip.easytripbackend.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByStatus(ApprovalStatus status);

    @Query("SELECT b FROM Bus b WHERE b.status = :status AND b.departureTime > :now " +
            "AND (:source IS NULL OR LOWER(b.source) = LOWER(:source)) " +
            "AND (:destination IS NULL OR LOWER(b.destination) = LOWER(:destination))")
    List<Bus> findApprovedBusesBySourceAndDestination(
            @Param("status") ApprovalStatus status,
            @Param("now") LocalDateTime now,
            @Param("source") String source,
            @Param("destination") String destination);
}
