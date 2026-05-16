package com.paulo_motors.demo.entitiesDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.entities.enums.MotorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CarDTO(
        @NotBlank String model,
        @NotBlank String brand,
        @NotBlank String color,
        @NotNull MotorType motor,
        @NotNull CarStatus status,

        @JsonProperty("pricePerDay")
        @NotNull @Positive BigDecimal pricePerDay,

        @JsonProperty("imageUrl")
        @NotBlank String imageUrl
) {}