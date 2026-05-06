package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Client;
import com.paulo_motors.demo.entitiesDTO.ClientDTO;
import com.paulo_motors.demo.repositories.ClientRepository;
import com.paulo_motors.demo.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientService service;

    private Client client;
    private ClientDTO clientDTO;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        client = new Client(id, "Paulo Silva", "paulo@gmail.com", "12345678901", "1699999999");
        clientDTO = new ClientDTO("Paulo Silva", "paulo@gmail.com", "12345678901", "1699999999");
    }

    @Test
    void findByIdShouldReturnClientWhenIdExists() {
        when(repository.findById(id)).thenReturn(Optional.of(client));
        Client result = service.findById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void createShouldReturnClient() {
        when(repository.save(any())).thenReturn(client);
        Client result = service.create(clientDTO);
        assertNotNull(result);
        assertEquals("Paulo Silva", result.getName());
    }

    @Test
    void deleteShouldThrowExceptionWhenIdDoesNotExist() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
    }
}