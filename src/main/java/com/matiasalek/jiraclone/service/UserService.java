package com.matiasalek.jiraclone.service;

import com.matiasalek.jiraclone.dto.request.AssignTicketRequest;
import com.matiasalek.jiraclone.dto.request.ChangePasswordRequest;
import com.matiasalek.jiraclone.dto.request.CreateUserRequest;
import com.matiasalek.jiraclone.dto.request.UpdateUserRequest;
import com.matiasalek.jiraclone.dto.response.AssignTicketResponse;
import com.matiasalek.jiraclone.dto.response.CreateUserResponse;
import com.matiasalek.jiraclone.dto.response.UpdateUserResponse;
import com.matiasalek.jiraclone.entity.Ticket;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import com.matiasalek.jiraclone.repository.TicketRepository;
import com.matiasalek.jiraclone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.AsyncWebRequestInterceptor;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;
    private final AsyncWebRequestInterceptor asyncWebRequestInterceptor;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TicketRepository ticketRepository, AsyncWebRequestInterceptor asyncWebRequestInterceptor) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository = ticketRepository;
        this.asyncWebRequestInterceptor = asyncWebRequestInterceptor;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    @Transactional
    public CreateUserResponse createUser(@Valid CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPasswordHash(hashedPassword);

        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());


        User savedUser = userRepository.save(user);
        return new CreateUserResponse(savedUser);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long id,@Valid UpdateUserRequest request) {
       User existingUser = userRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(id.toString()));

       request.getUsername().ifPresent(existingUser::setUsername);
       request.getEmail().ifPresent(existingUser::setEmail);
       request.getRole().ifPresent(existingUser::setRole);

       User updatedUser = userRepository.save(existingUser);
       return new UpdateUserResponse(updatedUser);
    }

    @Transactional
    public void changePassword(Long id, @Valid ChangePasswordRequest request) {
        User user =  userRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(id.toString()));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!request.getNewPassword().equals(request.getNewPasswordConfirmation())) {
            throw new IllegalArgumentException("New passwords do not match");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AssignTicketResponse assignTicket(Long TicketId, @Valid AssignTicketRequest request) {
        Ticket ticket = ticketRepository.findById(TicketId)
               .orElseThrow(() -> new EntityNotFoundException(TicketId.toString()));

        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(request.getAssigneeId().toString()));

        if (assignee.getRole() != Role.ADMIN && assignee.getRole() != Role.DEVELOPER) {
            throw new IllegalArgumentException("Assignee role must be either ADMIN or DEVELOPER");
        }

        ticket.setAssignee(assignee);

        Ticket updatedTicket = ticketRepository.save(ticket);
        return new AssignTicketResponse(updatedTicket);
    }

    // TODO Methods
    // Delete Users (Unassign tickets before deleting)(Who are those tickets going to be assigned to?)
    // Option: Assign all tickets to the reporter

    public boolean validatePassword(String username, String plainTextPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return passwordEncoder.matches(plainTextPassword, user.getPasswordHash());
    }
}
