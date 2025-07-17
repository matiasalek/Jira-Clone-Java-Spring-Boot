package com.matiasalek.jiraclone.dto.response;


import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UpdateUserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime updatedAt;

    public UpdateUserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.updatedAt = user.getUpdatedAt();
    }
}
