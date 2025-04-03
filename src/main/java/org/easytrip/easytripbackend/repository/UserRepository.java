package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsActive(boolean isActive);
}
