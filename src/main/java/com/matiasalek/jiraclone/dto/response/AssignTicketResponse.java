package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignTicketResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserSummary reporter;
    private UserSummary assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AssignTicketResponse(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.priority = ticket.getPriority();
        this.reporter = new UserSummary(ticket.getReporter());
        this.assignee = ticket.getAssignee() != null ? new UserSummary(ticket.getAssignee()) : null;
        this.createdAt = ticket.getCreatedAt();
        this.updatedAt = ticket.getUpdatedAt();
    }
}