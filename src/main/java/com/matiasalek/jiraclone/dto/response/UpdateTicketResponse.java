package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UpdateTicketResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserSummary reporter;
    private UserSummary assignee;
    private LocalDateTime updatedAt;

    public UpdateTicketResponse(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.priority = ticket.getPriority();
        this.updatedAt = ticket.getUpdatedAt();

        this.reporter = new UserSummary(
                ticket.getReporter().getId(),
                ticket.getReporter().getUsername(),
                ticket.getReporter().getEmail(),
                ticket.getReporter().getRole()
        );

        this.assignee = new UserSummary(
                ticket.getAssignee().getId(),
                ticket.getAssignee().getUsername(),
                ticket.getAssignee().getEmail(),
                ticket.getAssignee().getRole()
        );
    }
}
