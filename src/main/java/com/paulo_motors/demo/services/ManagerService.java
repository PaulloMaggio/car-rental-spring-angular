package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository repository;

    public Manager create(ManagerDTO dto) {
        Manager manager = new Manager();
        manager.setName(dto.name());
        manager.setLogin(dto.login());
        manager.setEmail(dto.email());
        manager.setPassword(dto.password());
        return repository.save(manager);
    }

    public List<Manager> findAll() {
        return repository.findAll();
    }

    public Manager findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No result found with ID: " + id));
    }

    public Manager update(UUID id, ManagerDTO dto) {
        Manager manager = findById(id);
        manager.setName(dto.name());
        manager.setLogin(dto.login());
        manager.setEmail(dto.email());
        manager.setPassword(dto.password());
        return repository.save(manager);
    }

    public void delete(UUID id) {
        Manager manager = findById(id);
        repository.delete(manager);
    }
}