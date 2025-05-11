package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BusOperatorRepository extends JpaRepository<BusOperator, Long> {
    @Query("SELECT bo FROM BusOperator bo WHERE bo.user.id = :userId")
    Optional<BusOperator> findByUserId(Long userId);
}
