package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.repositories.ManagerRepository;
import com.paulo_motors.demo.services.exceptions.DatabaseException;
import com.paulo_motors.demo.services.exceptions.ResourceAlreadyExistsException;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository repository;

    public Manager create(ManagerDTO dto) {
        try {
            Manager manager = new Manager();
            manager.setName(dto.name());
            manager.setLogin(dto.login());
            manager.setEmail(dto.email());
            manager.setPassword(dto.password());
            return repository.save(manager);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("email") || e.getMessage().contains("login")) {
                throw new ResourceAlreadyExistsException("Credentials already in use.");
            }
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Manager> findAll() {
        return repository.findAll();
    }

    public Manager findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Manager update(UUID id, ManagerDTO dto) {
        try {
            Manager manager = findById(id);
            manager.setName(dto.name());
            manager.setLogin(dto.login());
            manager.setEmail(dto.email());
            manager.setPassword(dto.password());
            return repository.save(manager);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        try {
            Manager manager = findById(id);
            repository.delete(manager);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}