package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository repository;

    public Manager createManager(ManagerDTO dto) {

        Manager manager = new Manager();

        manager.setName(dto.getName());
        manager.setLogin(dto.getLogin());
        manager.setEmail(dto.getEmail());
        manager.setPassword(dto.getPassword());

        return repository.save(manager);
    }

    public List<Manager> findAll() {
        List<Manager> list = repository.findAll();
        return list;
    }

    public Manager findById(UUID id) {
        Manager manager = new Manager();
        return manager = repository.findById(id).
                orElseThrow(() -> new RuntimeException("No result found with this ID" + id));
    }

    public Manager update(UUID id, ManagerDTO dto) {

        Manager manager = findById(id);

        manager.setName(dto.getName());
        manager.setLogin(dto.getLogin());
        manager.setEmail(dto.getEmail());
        manager.setPassword(dto.getPassword());

        return repository.save(manager);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
