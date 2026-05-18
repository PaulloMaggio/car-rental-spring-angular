package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    @Autowired
    private RentalService service;

    @PostMapping
    public ResponseEntity<Rental> save(@RequestBody @Valid RentalDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Rental>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Rental>> findByClient(@PathVariable UUID clientId) {
        return ResponseEntity.ok(service.findByClientId(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> update(@PathVariable UUID id, @RequestBody @Valid RentalDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}