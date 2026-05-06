package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/managers")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping
    public ResponseEntity<Manager> create(@RequestBody ManagerDTO dto) {
        Manager manager = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(manager.getId()).toUri();
        return ResponseEntity.created(uri).body(manager);
    }

    @GetMapping
    public ResponseEntity<List<Manager>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Manager> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Manager> update(@PathVariable UUID id, @RequestBody ManagerDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}