package com.matiasalek.jiraclone.service;

import com.matiasalek.jiraclone.dto.request.CreateUserRequest;
import com.matiasalek.jiraclone.dto.request.UpdateUserRequest;
import com.matiasalek.jiraclone.dto.response.CreateUserResponse;
import com.matiasalek.jiraclone.dto.response.UpdateUserResponse;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@Validated
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UpdateUserResponse updateUser(Long id,@Valid UpdateUserRequest request) {
       User existingUser = userRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(id.toString()));

       request.getUsername().ifPresent(existingUser::setUsername);
       request.getEmail().ifPresent(existingUser::setEmail);
       request.getRole().ifPresent(existingUser::setRole);

       User updatedUser = userRepository.save(existingUser);
       return new UpdateUserResponse(updatedUser);
    }

    // TODO Methods
    // Assign Tickets
    // Delete Users (Unassign tickets before deleting)(Who are those tickets going to be assigned to?)
    // Option: Assign all tickets to the reporter

    public boolean validatePassword(String username, String plainTextPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return passwordEncoder.matches(plainTextPassword, user.getPasswordHash());
    }
}
