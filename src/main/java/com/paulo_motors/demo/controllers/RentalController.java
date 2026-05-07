package com.paulo_motors.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@Tag(name = "Aluguéis")
public class RentalController {

    @Operation(summary = "Listar aluguéis")
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar aluguel por ID")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Registrar novo aluguel")
    @PostMapping
    public ResponseEntity save() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Realizar devolução de veículo")
    @PutMapping("/return")
    public ResponseEntity returnCar() {
        return ResponseEntity.ok().build();
    }
}