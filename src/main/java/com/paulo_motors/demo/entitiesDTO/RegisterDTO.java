package com.paulo_motors.demo.entitiesDTO;

import com.paulo_motors.demo.entities.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
        @NotBlank String login,
        @NotBlank String password,
        @NotNull UserRole role
) {}