package org.easytrip.easytripbackend.repository;

import org.easytrip.easytripbackend.model.Role;
import org.easytrip.easytripbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsActive(boolean isActive);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
    long countByEmailWithRoles(@Param("email") String email);
}



































