package com.paulo_motors.demo.entitiesDTO;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record ReturnDTO(
        @NotNull UUID rentalId,
        @NotNull Instant returnDate
) {}