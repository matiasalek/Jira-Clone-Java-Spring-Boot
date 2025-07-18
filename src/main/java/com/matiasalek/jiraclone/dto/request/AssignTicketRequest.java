package com.matiasalek.jiraclone.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTicketRequest {
    @NotNull(message = "Assignee ID is required")
    private Long assigneeId;
}
