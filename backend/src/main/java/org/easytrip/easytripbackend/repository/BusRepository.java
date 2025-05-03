package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
