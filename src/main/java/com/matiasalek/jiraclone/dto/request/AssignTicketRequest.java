package com.matiasalek.jiraclone.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTicketRequest {
    @NotNull(message = "Assignee ID is required")
    private Long assigneeId;
}
