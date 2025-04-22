package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request body for user login")
public class LoginRequest {
    @NotBlank
    @Email
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @NotBlank
    @Schema(description = "User's password", example = "password123")
    private String password;

}
