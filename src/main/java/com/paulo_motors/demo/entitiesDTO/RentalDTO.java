package com.paulo_motors.demo.entitiesDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record RentalDTO(
        @NotNull(message = "Start date is required")
        @FutureOrPresent(message = "Start date cannot be in the past")
        Instant startDate,

        @NotNull(message = "End date is required")
        @Future(message = "End date must be in the future")
        Instant endDate,

        @NotNull(message = "Client ID is required")
        UUID clientId,

        @NotNull(message = "Car ID is required")
        UUID carId
) {}