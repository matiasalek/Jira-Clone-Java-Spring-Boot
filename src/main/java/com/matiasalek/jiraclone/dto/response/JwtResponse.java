package com.matiasalek.jiraclone.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "JWT Response with token and user info")
public class JwtResponse {
    @Schema(description = "JWT Access Token")
    private String token;

    @Schema(description = "Token type", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "Username")
    private String username;

    @Schema(description = "User role", example = "DEVELOPER")
    private String role;

    @Schema(description = "User ID")
    private Long userId;

    public JwtResponse(String token, String username, String name, Long id) {
        this.token = token;
        this.username = username;
        this.role = name;
        this.userId = id;
    }
}
