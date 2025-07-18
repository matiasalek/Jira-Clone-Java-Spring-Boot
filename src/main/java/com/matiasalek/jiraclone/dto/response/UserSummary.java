package com.matiasalek.jiraclone.dto.response;

import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    private Long id;
    private String username;
    private String email;
    private Role role;

    public UserSummary(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
