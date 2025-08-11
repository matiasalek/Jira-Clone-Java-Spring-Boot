package com.matiasalek.jiraclone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
    private Long userId;

    public JwtResponse(String token, String username, String name, Long id) {
        this.token = token;
        this.username = username;
        this.role = name;
        this.userId = id;
    }
}
