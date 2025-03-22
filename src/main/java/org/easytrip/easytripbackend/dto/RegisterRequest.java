package org.easytrip.easytripbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description="Request body for user registration")
public class RegisterRequest {
    @NotBlank
    @Schema(description = "Full name of the user", example = "Dipesh Ghimire")
    private String name;

    @NotBlank
    @Email
    @Schema(description = "User's email address", example = "dip.ghi@example.com")
    private String email;

    @NotBlank
    @Size(min = 6, max = 16)
    @Schema(description = "Password (minimum 6 characters)", example = "password123")
    private String password;

    @NotBlank
    @Size(min = 6, max = 16)
    @Schema(description = "Password confirmation", example = "password123")
    private String confirmPassword;
}
