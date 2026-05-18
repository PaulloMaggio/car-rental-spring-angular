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
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private EmailService emailService;

    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Rental> findByClientId(UUID clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return repository.findByClient(client);
    }

    @Transactional
    public Rental create(RentalDTO dto) {
        System.out.println("📥 Iniciando criação de rental - Client: " + dto.clientId() + " | Car: " + dto.carId());

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Car car = carRepository.findById(dto.carId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new DatabaseException("Car is not available");
        }

        Rental rental = new Rental();
        rental.setStartDate(dto.startDate());
        rental.setEndDate(dto.endDate());
        rental.setClient(client);
        rental.setCar(car);
        rental.setPriceAtRental(car.getPricePerDay());

        long days = Duration.between(dto.startDate(), dto.endDate()).toDays();
        if (days <= 0) days = 1;

        BigDecimal total = car.getPricePerDay().multiply(new BigDecimal(days));
        rental.setTotalValue(total);

        car.setStatus(CarStatus.RENTED);
        carRepository.save(car);

        Rental savedRental = repository.saveAndFlush(rental);

        System.out.println("✅ Rental salvo com ID: " + savedRental.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String emailDestino = client.getEmail();
        String nomeCliente = client.getName();
        String detalhesCarro = car.getBrand() + " " + car.getModel();
        String dataInicioStr = savedRental.getStartDate().atZone(ZoneId.systemDefault()).format(formatter);
        String dataFimStr = savedRental.getEndDate().atZone(ZoneId.systemDefault()).format(formatter);
        BigDecimal valorTotal = savedRental.getTotalValue();
        String corCarro = car.getColor();
        String motorCarro = car.getMotor() != null ? car.getMotor().name() : "";

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    emailService.sendRentalConfirmation(
                            emailDestino,
                            nomeCliente,
                            detalhesCarro,
                            dataInicioStr,
                            dataFimStr,
                            valorTotal,
                            corCarro,
                            motorCarro
                    );
                    System.out.println("📧 Email assíncrono disparado pós-commit para: " + emailDestino);
                } catch (Exception e) {
                    System.err.println("❌ Falha ao enviar email no fluxo assíncrono: " + e.getMessage());
                }
            }
        });

        return savedRental;
    }

    @Transactional
    public Rental update(UUID id, RentalDTO dto) {
        Rental rental = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        rental.setStartDate(dto.startDate());
        rental.setEndDate(dto.endDate());

        long days = Duration.between(dto.startDate(), dto.endDate()).toDays();
        if (days <= 0) days = 1;

        BigDecimal total = rental.getPriceAtRental().multiply(new BigDecimal(days));
        rental.setTotalValue(total);

        return repository.save(rental);
    }

    @Transactional
    public void delete(UUID id) {
        Rental rental = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        Car car = rental.getCar();
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        repository.delete(rental);
    }
}