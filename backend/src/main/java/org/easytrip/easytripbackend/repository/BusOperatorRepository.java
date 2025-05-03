package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusOperatorRepository extends JpaRepository<BusOperator, Long> {
    Optional<BusOperator> findByUserId(Long userId);
}
