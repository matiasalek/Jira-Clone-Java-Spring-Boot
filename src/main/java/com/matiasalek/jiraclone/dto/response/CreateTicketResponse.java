package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTicketResponse {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserSummary reporter;
    private UserSummary assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   public CreateTicketResponse(Ticket ticket) {
       this.id = ticket.getId();
       this.title = ticket.getTitle();
       this.description = ticket.getDescription();
       this.status = ticket.getStatus();
       this.priority = ticket.getPriority();
       this.createdAt = ticket.getCreatedAt();
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
