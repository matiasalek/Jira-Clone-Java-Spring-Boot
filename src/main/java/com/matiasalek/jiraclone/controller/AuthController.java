package com.matiasalek.jiraclone.controller;

import com.matiasalek.jiraclone.dto.request.CreateUserRequest;
import com.matiasalek.jiraclone.dto.request.LoginRequest;
import com.matiasalek.jiraclone.dto.request.RegisterRequest;
import com.matiasalek.jiraclone.dto.response.JwtResponse;
import com.matiasalek.jiraclone.dto.response.MessageResponse;
import com.matiasalek.jiraclone.entity.User;
import com.matiasalek.jiraclone.enums.Role;
import com.matiasalek.jiraclone.security.JwtUtil;
import com.matiasalek.jiraclone.service.CustomUserDetailsService;
import com.matiasalek.jiraclone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = JwtResponse.class))),

            @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            User user = (User) userDetails;

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
    @Operation(summary = "User registration", description = "Register new user as DEVELOPER")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            CreateUserRequest createRequest = new CreateUserRequest();
            createRequest.setUsername(request.getUsername());
            createRequest.setEmail(request.getEmail());
            createRequest.setPassword(request.getPassword());
            createRequest.setRole(Role.DEVELOPER);

            userService.createUser(createRequest);

            return ResponseEntity.ok(new MessageResponse("User registered successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

}
