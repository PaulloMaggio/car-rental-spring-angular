package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.entities.enums.MotorType;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.repositories.ClientRepository;
import com.paulo_motors.demo.repositories.RentalRepository;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private RentalRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private RentalService service;

    private RentalDTO rentalDTO;
    private Client client;
    private Car car;
    private UUID clientId;
    private UUID carId;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        carId = UUID.randomUUID();
        client = new Client(clientId, "Paulo", "paulo@test.com", "12345678901", "1699999999");
        car = new Car(carId, "Civic", "Honda", "Black", MotorType.MOTOR_2_0, CarStatus.AVAILABLE, new BigDecimal("200.00"));

        rentalDTO = new RentalDTO(
                Instant.now().plus(1, ChronoUnit.DAYS),
                Instant.now().plus(5, ChronoUnit.DAYS),
                clientId,
                carId
        );
    }

    @Test
    void createShouldReturnRentalWhenDataIsValid() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Rental result = service.create(rentalDTO);

        assertNotNull(result);
        assertEquals(CarStatus.RENTED, car.getStatus());
        assertNotNull(result.getTotalValue());
        assertEquals(new BigDecimal("800.00"), result.getTotalValue());
    }

    @Test
    void createShouldThrowResourceNotFoundExceptionWhenCarDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(rentalDTO));
    }
}