package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entitiesDTO.CarDTO;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.services.exceptions.DatabaseException;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    public Car create(CarDTO dto) {
        try {
            Car car = new Car();
            updateData(car, dto);
            return repository.save(car);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Car findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Car update(UUID id, CarDTO dto) {
        try {
            Car car = findById(id);
            updateData(car, dto);
            return repository.save(car);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        try {
            Car car = findById(id);
            repository.delete(car);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void updateData(Car car, CarDTO dto) {
        car.setModel(dto.model());
        car.setBrand(dto.brand());
        car.setColor(dto.color());
        car.setMotor(dto.motor());
        car.setStatus(dto.status());
        car.setPricePerDay(dto.pricePerDay());
    }
}