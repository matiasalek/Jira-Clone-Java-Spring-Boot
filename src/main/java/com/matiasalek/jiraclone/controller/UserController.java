package com.matiasalek.jiraclone.controller;

import com.matiasalek.jiraclone.dto.request.AssignTicketRequest;
import com.matiasalek.jiraclone.dto.request.ChangePasswordRequest;
import com.matiasalek.jiraclone.dto.request.CreateUserRequest;
import com.matiasalek.jiraclone.dto.request.UpdateUserRequest;
import com.matiasalek.jiraclone.dto.response.AssignTicketResponse;
import com.matiasalek.jiraclone.dto.response.CreateUserResponse;
import com.matiasalek.jiraclone.dto.response.UpdateUserResponse;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest user) {
        CreateUserResponse createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        UpdateUserResponse updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest user) {
        userService.changePassword(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/assign-ticket")
    public ResponseEntity<AssignTicketResponse> assignTicket(@PathVariable Long id, @RequestBody AssignTicketRequest ticketUser) {
        AssignTicketResponse assignTicket = userService.assignTicket(id, ticketUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignTicket);
    }
}
