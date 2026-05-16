package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entitiesDTO.CarDTO;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Car findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public Car create(CarDTO dto) {
        Car entity = new Car();
        copyDtoToEntity(dto, entity);
        return repository.save(entity);
    }

    @Transactional
    public Car update(UUID id, CarDTO dto) {
        try {
            Car entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return repository.saveAndFlush(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        repository.deleteById(id);
    }

    private void copyDtoToEntity(CarDTO dto, Car entity) {
        entity.setModel(dto.model());
        entity.setBrand(dto.brand());
        entity.setColor(dto.color());
        entity.setMotor(dto.motor());
        entity.setStatus(dto.status());
        entity.setPricePerDay(dto.pricePerDay());
        entity.setImageUrl(dto.imageUrl());
    }
}