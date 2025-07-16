package com.matiasalek.jiraclone.repository;

import com.matiasalek.jiraclone.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
