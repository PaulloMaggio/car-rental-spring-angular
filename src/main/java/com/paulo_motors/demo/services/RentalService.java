package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.entitiesDTO.ReturnDTO;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.repositories.ClientRepository;
import com.paulo_motors.demo.repositories.RentalRepository;
import com.paulo_motors.demo.services.exceptions.DatabaseException;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CarRepository carRepository;

    @Transactional
    public Rental create(RentalDTO dto) {
        if (dto.startDate().isBefore(Instant.now())) {
            throw new DatabaseException("Start date cannot be in the past");
        }
        if (dto.endDate().isBefore(dto.startDate())) {
            throw new DatabaseException("End date must be after start date");
        }

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new DatabaseException("Car is not available for rental");
        }

        Rental rental = new Rental();
        rental.setStartDate(dto.startDate());
        rental.setEndDate(dto.endDate());
        rental.setClient(client);
        rental.setCar(car);
        rental.setPriceAtRental(car.getPricePerDay());

        long days = Duration.between(dto.startDate(), dto.endDate()).toDays();
        if (days <= 0) days = 1;

        rental.setTotalValue(car.getPricePerDay().multiply(new BigDecimal(days)));

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        return repository.saveAndFlush(rental);
    }

    @Transactional
    public Rental processReturn(ReturnDTO dto) {
        Rental rental = repository.findById(dto.rentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        Car car = rental.getCar();

        if (car.getStatus() == CarStatus.AVAILABLE) {
            throw new DatabaseException("This rental has already been closed");
        }

        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        if (dto.returnDate().isAfter(rental.getEndDate())) {
            long extraDays = Duration.between(rental.getEndDate(), dto.returnDate()).toDays();
            if (extraDays > 0) {
                BigDecimal fine = rental.getPriceAtRental().multiply(new BigDecimal(extraDays));
                rental.setTotalValue(rental.getTotalValue().add(fine));
            }
        }

        rental.setEndDate(dto.returnDate());
        return repository.saveAndFlush(rental);
    }

    @Transactional(readOnly = true)
    public Page<Rental> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Rental findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}