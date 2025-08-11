package com.matiasalek.jiraclone.controller;

import com.matiasalek.jiraclone.dto.request.CreateTicketRequest;
import com.matiasalek.jiraclone.dto.request.UpdateTicketRequest;
import com.matiasalek.jiraclone.dto.response.CreateTicketResponse;
import com.matiasalek.jiraclone.dto.response.TicketSummary;
import com.matiasalek.jiraclone.dto.response.UpdateTicketResponse;
import com.matiasalek.jiraclone.enums.Status;
import com.matiasalek.jiraclone.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketSummary> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketSummary> getTicketById(@PathVariable Long id) {
        TicketSummary ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/status/{status}")
    public List<TicketSummary> getTicketsByStatus(@PathVariable String status) {
        Status statusEnum = Status.valueOf(status.toUpperCase());
        return ticketService.getAllTicketsByStatus(statusEnum);
    }

    @PostMapping
    public ResponseEntity<CreateTicketResponse> createTicket(@Valid @RequestBody CreateTicketRequest createTicketRequest) {
        CreateTicketResponse createTicketResponse = ticketService.createTicket(createTicketRequest);
        return ResponseEntity.ok(createTicketResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTicketResponse> updateTicket(@PathVariable Long id, @RequestBody UpdateTicketRequest updateTicketRequest) {
        UpdateTicketResponse updatedTicket = ticketService.updateTicket(id, updateTicketRequest);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok().build();
    }
}
