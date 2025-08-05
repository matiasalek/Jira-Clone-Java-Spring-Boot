package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CreateTicketResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private User reporter;
    private User assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   public CreateTicketResponse(Ticket ticket) {
       this.id = ticket.getId();
       this.title = ticket.getTitle();
       this.description = ticket.getDescription();
       this.status = ticket.getStatus();
       this.priority = ticket.getPriority();
       this.reporter = ticket.getReporter();
       this.assignee = ticket.getAssignee();
       this.createdAt = ticket.getCreatedAt();
       this.updatedAt = ticket.getUpdatedAt();
   }
}
