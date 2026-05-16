package com.paulo_motors.demo.entitiesDTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String nome,
        @NotBlank String login,
        @NotBlank String password
) {}