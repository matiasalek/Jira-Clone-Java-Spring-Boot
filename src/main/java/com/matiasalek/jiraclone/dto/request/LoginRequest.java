package com.matiasalek.jiraclone.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Login request payload")
public class LoginRequest {
    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example="matias_alek")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password", example="verySecurePassword")
    private String password;
}
