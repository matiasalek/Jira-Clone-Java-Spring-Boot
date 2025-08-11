package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSummary {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserSummary reporter;
    private UserSummary assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TicketSummary(Ticket ticket, UserSummary reporter, UserSummary assignee) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.priority = ticket.getPriority();
        this.reporter = reporter;
        this.assignee = assignee;
        this.createdAt = ticket.getCreatedAt();
        this.updatedAt = ticket.getUpdatedAt();
    }
}
