package com.paulo_motors.demo.repositories;

import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByClient(Client client);
}