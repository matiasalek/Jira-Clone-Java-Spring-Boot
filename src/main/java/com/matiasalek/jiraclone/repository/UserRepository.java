package com.matiasalek.jiraclone.repository;

import com.matiasalek.jiraclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
