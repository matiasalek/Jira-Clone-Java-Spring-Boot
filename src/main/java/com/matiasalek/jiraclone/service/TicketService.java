package com.matiasalek.jiraclone.service;

import com.matiasalek.jiraclone.dto.request.CreateTicketRequest;
import com.matiasalek.jiraclone.dto.request.UpdateTicketRequest;
import com.matiasalek.jiraclone.dto.response.CreateTicketResponse;
import com.matiasalek.jiraclone.dto.response.UpdateTicketResponse;
import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import com.matiasalek.jiraclone.exception.UnauthorizedException;
import com.matiasalek.jiraclone.repository.TicketRepository;
import com.matiasalek.jiraclone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@Validated
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Ticket not found"+ id));
    }

    @Transactional
    public CreateTicketResponse createTicket(@Valid CreateTicketRequest request) {
        Role role = userRepository.findRoleByUserId(request.getReporterId());

        if (role != Role.ADMIN) {
            throw new UnauthorizedException("Only ADMINs can create tickets.");
        }

        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(()-> new EntityNotFoundException("Reporter not found"+ request.getReporterId()));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(()-> new EntityNotFoundException("Assignee not found"+ request.getAssigneeId()));

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(request.getStatus());
        ticket.setPriority(request.getPriority());
        ticket.setReporter(reporter);
        ticket.setAssignee(assignee);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);
        return new CreateTicketResponse(savedTicket);
    }

    @Transactional
    public UpdateTicketResponse updateTicket(Long id, @Valid UpdateTicketRequest updateTicketRequest) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Ticket not found"+ id));

        updateTicketRequest.getTitle().ifPresent(existingTicket::setTitle);
        updateTicketRequest.getDescription().ifPresent(existingTicket::setDescription);
        updateTicketRequest.getStatus().ifPresent(existingTicket::setStatus);
        updateTicketRequest.getPriority().ifPresent(existingTicket::setPriority);
        updateTicketRequest.getReporter().ifPresent(existingTicket::setReporter);
        updateTicketRequest.getAssignee().ifPresent(existingTicket::setAssignee);

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        return new UpdateTicketResponse(updatedTicket);
    }
}
