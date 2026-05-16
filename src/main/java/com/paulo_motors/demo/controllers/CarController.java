package com.paulo_motors.demo.controllers;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entitiesDTO.CarDTO;
import com.paulo_motors.demo.services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@Tag(name = "Carros")
@CrossOrigin(origins = "*")
public class CarController {

    @Autowired
    private CarService service;

    @GetMapping
    public ResponseEntity<List<Car>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Cadastrar novo veículo")
    @PostMapping
    public ResponseEntity<Car> save(@RequestBody @Valid CarDTO dto) {
        Car car = service.create(dto);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable UUID id, @RequestBody @Valid CarDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}