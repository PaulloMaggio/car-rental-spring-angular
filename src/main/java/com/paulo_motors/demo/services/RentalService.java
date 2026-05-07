package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entities.Rental;
import com.paulo_motors.demo.entitiesDTO.RentalDTO;
import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.repositories.ClientRepository;
import com.paulo_motors.demo.repositories.RentalRepository;
import com.paulo_motors.demo.services.exceptions.DatabaseException;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
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

        long days = Duration.between(dto.startDate(), dto.endDate()).toDays();
        if (days <= 0) days = 1;

        BigDecimal totalPrice = car.getPricePerDay().multiply(new BigDecimal(days));
        rental.setTotalValue(totalPrice);

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        return repository.saveAndFlush(rental);
    }

    public List<Rental> findAll() {
        return repository.findAll();
    }

    public Rental findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}