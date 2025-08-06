package com.matiasalek.jiraclone.dto.request;

import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTicketRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required. Status can be TODO, IN_PROGRESS or DONE")
    private Status status;

    @NotNull(message = "Priority is required. Priority can be LOW, MEDIUM, HIGH or URGENT")
    private Priority priority;

    @NotNull(message = "User to report the ticket is required")
    private Long reporterId;

    @NotNull(message = "User to assign the ticket is required")
    private Long assigneeId;
}
