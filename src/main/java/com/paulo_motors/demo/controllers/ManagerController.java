package com.paulo_motors.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/managers")
@Tag(name = "Gerentes")
public class ManagerController {

    @Operation(summary = "Listar todos os gerentes")
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar gerente por ID")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cadastrar novo gerente")
    @PostMapping
    public ResponseEntity save() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar dados do gerente")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover gerente do sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }
}