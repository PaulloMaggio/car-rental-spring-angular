package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/rentals")
public class RentalController {

    @Autowired
    private RentalService service;

    @PostMapping
    public ResponseEntity<Rental> create(@Valid @RequestBody RentalDTO dto) {
        Rental rental = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(rental.getId()).toUri();
        return ResponseEntity.created(uri).body(rental);
    }

    @GetMapping
    public ResponseEntity<List<Rental>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Rental> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }
}