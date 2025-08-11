package com.matiasalek.jiraclone.repository;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(Status status);
    List<Ticket> findByPriority(Priority priority);
}
