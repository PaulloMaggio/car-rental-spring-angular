package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entitiesDTO.CarDTO;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.entities.enums.MotorType;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarService service;

    private Car car;
    private CarDTO carDTO;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        car = new Car(id, "Onix", "Chevrolet", "Black", MotorType.MOTOR_1_0, CarStatus.AVAILABLE);
        carDTO = new CarDTO("Onix", "Chevrolet", "Black", MotorType.MOTOR_1_0, CarStatus.AVAILABLE);
    }

    @Test
    void findByIdShouldReturnCarWhenIdExists() {
        when(repository.findById(id)).thenReturn(Optional.of(car));
        Car result = service.findById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void createShouldReturnCar() {
        when(repository.save(any())).thenReturn(car);
        Car result = service.create(carDTO);
        assertNotNull(result);
        assertEquals("Onix", result.getModel());
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(repository.findById(id)).thenReturn(Optional.of(car));
        doNothing().when(repository).delete(car);
        assertDoesNotThrow(() -> service.delete(id));
        verify(repository, times(1)).delete(car);
    }
}