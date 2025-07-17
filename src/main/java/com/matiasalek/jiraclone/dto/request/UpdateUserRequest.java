package com.matiasalek.jiraclone.dto.request;

import com.matiasalek.jiraclone.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;

@Data
public class UpdateUserRequest {
    @Valid
    private Optional<@NotBlank @Size(min = 5, max = 20) String> username = Optional.empty();

    @Valid
    private Optional<@NotBlank @Email(message = "Invalid email address") String> email = Optional.empty();

    @Valid
    private Optional<@NotNull(message = "Role must be either ADMIN or DEVELOPER") Role> role = Optional.empty();
}
