package com.paulo_motors.demo.entitiesDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 11, message = "CPF must have 11 digits")
        String cpf,

        @NotBlank(message = "Phone is required")
        String phone
) {}