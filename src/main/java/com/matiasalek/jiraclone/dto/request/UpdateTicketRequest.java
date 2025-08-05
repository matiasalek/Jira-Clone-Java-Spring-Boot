package com.matiasalek.jiraclone.dto.request;

import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Priority;
import com.matiasalek.jiraclone.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketRequest {
    @Valid
    private Optional<@NotBlank String> title = Optional.empty();

    @Valid
    private Optional<@NotBlank String> description = Optional.empty();

    @Valid
    private Optional<@NotNull Status> status = Optional.empty();

    @Valid
    private Optional<@NotNull Priority> priority = Optional.empty();

    @Valid
    private Optional<@NotNull User> reporter = Optional.empty();

    @Valid
    private Optional<@NotBlank User> assignee = Optional.empty();
}
