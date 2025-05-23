package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.easytrip.easytripbackend.dto.LoginRequest;
import org.easytrip.easytripbackend.dto.RegisterRequest;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.model.UserResponseDTO;
import org.easytrip.easytripbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication", description="API for user registration and Login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
        @Operation(summary="Register a new User", description="Creates a new user account with name, email, and password. Default role is client.")
        @ApiResponses(value={
            @ApiResponse(responseCode="200", description="User registered successfully",
            content=@Content(mediaType="application/json", schema=@Schema(implementation=User.class))),
                @ApiResponse(responseCode="400", description="invalid input(e.g., passwords don’t match or email exists)",
                        content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns a JWT token for accessing protected endpoints.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Invalid email or password",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        System.out.println("Login request received for: " + request.getEmail()); // Debug log
        Map<String, String> tokens = authService.login(request);
        System.out.println("generated tokens: "+tokens);
        return ResponseEntity.ok(tokens);
    }



    @GetMapping()
    @Operation(summary =" Get Inactive users ")
    @ApiResponse(responseCode = "200", description = "Inactive Users lists")
    public ResponseEntity<List<UserResponseDTO>> getInactiveUsers() {
       return ResponseEntity.ok(authService.getDeactiveUsers(false));
    }

    @GetMapping("/users/active")
    @Operation(summary =" Get active users ")
    @ApiResponse(responseCode = "200", description = "Users lists")
    public ResponseEntity<List<UserResponseDTO>> getActiveUsers() {
        return ResponseEntity.ok(authService.getActiveUsers(true));
    }


    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New access token returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<String> refresh(@RequestBody String refreshToken) {
        String newAccessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}