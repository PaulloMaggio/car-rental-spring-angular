package com.paulo_motors.demo.entitiesDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ManagerDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Login is required")
        String login,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must have at least 6 characters")
        String password
) {}