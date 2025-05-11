package org.easytrip.easytripbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.easytrip.easytripbackend.model.User;
import org.easytrip.easytripbackend.model.UserResponseDTO;
import org.easytrip.easytripbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin API", description = "Endpoints for admin user management")
public class AdminController {
    @Autowired
    private AuthService authService;

    @PostMapping("/users/{id}/deactivate")
    @Operation(
            summary = "Deactivate a user",
            description = "Deactivates a user by ID. Only admins can perform this action. Cannot deactivate admin users."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot deactivate an admin user"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    public ResponseEntity<String> deactivateUser(@Parameter(description = "ID of the user to deactivate", required = true) @PathVariable Long id){
        authService.deactivateUser(id);
        return ResponseEntity.ok("Deactivated");
    }



    @PostMapping("/users/{id}/activate")
    @Operation(
            summary="Activate a user",
            description="Activates a user by ID. Only admins can perform this action. "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description="Users activated successfully"),
            @ApiResponse(responseCode="403", description="Forbidden - requires ADMIN role"),
            @ApiResponse(responseCode="404", description ="User not found")
    })
    public ResponseEntity<String> activateUser(@Parameter(description="Id of the users to reactivate", required=true) @PathVariable Long id){
        authService.activateUser(id);
        return ResponseEntity.ok("Activated");
    }

    @GetMapping("/users")
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of users filtered by active status. Only admins can access this endpoint."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Requires ADMIN role")
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@Parameter(description = "Filter users by active status (true/false)", required = false) @RequestParam(required = false, defaultValue="true") boolean isActive){
        return ResponseEntity.ok(authService.getActiveUsers(true));
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "Welcome to Admin Dashboard";
    }
}
