package com.paulo_motors.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Clientes")
public class ClientController {

    @Operation(summary = "Listar todos os clientes")
    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cadastrar novo cliente")
    @PostMapping
    public ResponseEntity save() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualizar dados do cliente")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Excluir cadastro de cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        return ResponseEntity.ok().build();
    }
}