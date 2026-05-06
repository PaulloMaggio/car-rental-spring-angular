package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(name = "/managers")
public class ManagerController {

    @Autowired
    private ManagerService service;

    @PostMapping
    public ResponseEntity<Manager> saveManager(@RequestBody ManagerDTO dto) {
        Manager manager = service.createManager(dto);
        return ResponseEntity.ok().body(manager);
    }

    @GetMapping
    public ResponseEntity<List<Manager>> searchAll() {
        List<Manager> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Manager> serachById(@PathVariable UUID id) {
        Manager manager = service.findById(id);
        return ResponseEntity.ok().body(manager);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Manager> searchAndUpdate(@PathVariable UUID id, @RequestBody ManagerDTO dto) {
        Manager manager = service.update(id, dto);
        return ResponseEntity.ok().body(manager);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> searchAndDelete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
