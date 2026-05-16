package com.paulo_motors.demo.entitiesDTO;

import java.util.UUID;

public record LoginResponseDTO(String token, String role, UUID id) {
}