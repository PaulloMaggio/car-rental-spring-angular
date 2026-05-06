package com.paulo_motors.demo.entitiesDTO;

import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.entities.enums.MotorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarDTO(
        @NotBlank(message = "Model is required")
        String model,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotBlank(message = "Color is required")
        String color,

        @NotNull(message = "Motor type is required")
        MotorType motor,

        @NotNull(message = "Car status is required")
        CarStatus status
) {}