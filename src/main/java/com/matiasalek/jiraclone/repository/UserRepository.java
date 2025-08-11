package com.matiasalek.jiraclone.repository;

import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.id = :userId")
    Role findRoleByUserId(@Param("userId") Long userId);

    Optional<User> findByUsername(String username);
}
