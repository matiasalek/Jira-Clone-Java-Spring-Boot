package com.matiasalek.jiraclone.controller;

import com.matiasalek.jiraclone.dto.request.CreateUserRequest;
import com.matiasalek.jiraclone.dto.request.LoginRequest;
import com.matiasalek.jiraclone.dto.request.RegisterRequest;
import com.matiasalek.jiraclone.dto.response.CreateUserResponse;
import com.matiasalek.jiraclone.dto.response.JwtResponse;
import com.matiasalek.jiraclone.dto.response.MessageResponse;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import com.matiasalek.jiraclone.security.JwtUtil;
import com.matiasalek.jiraclone.service.CustomUserDetailsService;
import com.matiasalek.jiraclone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            // Cast to get additional user info
            User user = (User) userDetails;

            // Return JWT response
            JwtResponse jwtResponse = new JwtResponse(
                    token,
                    user.getUsername(),
                    user.getRole().name(),
                    user.getId()
            );

            return ResponseEntity.ok(jwtResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Create a CreateUserRequest with DEVELOPER role
            CreateUserRequest createRequest = new CreateUserRequest();
            createRequest.setUsername(request.getUsername());
            createRequest.setEmail(request.getEmail());
            createRequest.setPassword(request.getPassword());
            createRequest.setRole(Role.DEVELOPER); // Force DEVELOPER role

            // Use your existing createUser method
            CreateUserResponse response = userService.createUser(createRequest);

            return ResponseEntity.ok(new MessageResponse("User registered successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

}
