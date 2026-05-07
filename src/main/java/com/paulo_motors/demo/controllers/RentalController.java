package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.entitiesDTO.ReturnDTO;
import com.paulo_motors.demo.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PutMapping(value = "/return")
    public ResponseEntity<Rental> processReturn(@Valid @RequestBody ReturnDTO dto) {
        Rental rental = service.processReturn(dto);
        return ResponseEntity.ok().body(rental);
    }

    @GetMapping
    public ResponseEntity<Page<Rental>> findAll(Pageable pageable) {
        Page<Rental> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Rental> findById(@PathVariable UUID id) {
        Rental rental = service.findById(id);
        return ResponseEntity.ok().body(rental);
    }
}