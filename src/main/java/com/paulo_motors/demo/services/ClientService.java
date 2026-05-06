package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entitiesDTO.ClientDTO;
import com.paulo_motors.demo.repositories.ClientRepository;
import com.paulo_motors.demo.services.exceptions.DatabaseException;
import com.paulo_motors.demo.services.exceptions.ResourceAlreadyExistsException;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public Client create(ClientDTO dto) {
        try {
            Client client = new Client();
            updateData(client, dto);
            return repository.save(client);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("cpf") || e.getMessage().contains("email")) {
                throw new ResourceAlreadyExistsException("CPF or Email already in use.");
            }
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Client update(UUID id, ClientDTO dto) {
        try {
            Client client = findById(id);
            updateData(client, dto);
            return repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void delete(UUID id) {
        try {
            Client client = findById(id);
            repository.delete(client);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void updateData(Client client, ClientDTO dto) {
        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setCpf(dto.cpf());
        client.setPhone(dto.phone());
    }
}