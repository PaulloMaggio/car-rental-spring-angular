package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.enums.CarStatus;
import com.paulo_motors.demo.repositories.CarRepository;
import com.paulo_motors.demo.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentalTaskService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRepository carRepository;

    @Scheduled(fixedRate = 600000)
    @Transactional
    public void resetDatabaseForTesting() {
        try {
            rentalRepository.deleteAll();

            carRepository.findAll().forEach(car -> {
                if (car.getStatus() != CarStatus.AVAILABLE) {
                    car.setStatus(CarStatus.AVAILABLE);
                    carRepository.save(car);
                }
            });

            System.out.println("🔄 BANCO DE DADOS RESETADO: Reservas limpas e carros liberados!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao resetar banco de dados: " + e.getMessage());
        }
    }
}