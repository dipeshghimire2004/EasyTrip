package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Guesthouse;
import org.easytrip.easytripbackend.model.GuesthouseApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuesthouseRepository extends JpaRepository<Guesthouse, Long> {
//    List<Guesthouse> findByLocationContainingIgnoreCase(String location);   //to perform partial match search
    List<Guesthouse> findByOwnerId(Long ownerId);
    List<Guesthouse> findByStatus(GuesthouseApprovalStatus status);   //for admin pending approvals

}
