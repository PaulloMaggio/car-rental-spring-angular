package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Manager;
import com.paulo_motors.demo.entitiesDTO.ManagerDTO;
import com.paulo_motors.demo.repositories.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository repository;

    @InjectMocks
    private ManagerService service;

    private Manager manager;
    private ManagerDTO managerDTO;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        manager = new Manager(id, "Paulo", "paulo_admin", "paulo@test.com", "123");
        managerDTO = new ManagerDTO("Paulo", "paulo_admin", "paulo@test.com", "123");
    }

    @Test
    void createShouldReturnManager() {
        when(repository.save(any(Manager.class))).thenReturn(manager);

        Manager result = service.create(managerDTO);

        assertNotNull(result);
        assertEquals(managerDTO.name(), result.getName());
        verify(repository, times(1)).save(any(Manager.class));
    }

    @Test
    void findAllShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(manager));

        List<Manager> result = service.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnManagerWhenIdExists() {
        when(repository.findById(id)).thenReturn(Optional.of(manager));

        Manager result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findByIdShouldThrowExceptionWhenIdDoesNotExist() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    void updateShouldReturnUpdatedManager() {
        when(repository.findById(id)).thenReturn(Optional.of(manager));
        when(repository.save(any(Manager.class))).thenReturn(manager);

        Manager result = service.update(id, managerDTO);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Manager.class));
    }

    @Test
    void deleteShouldCallRepositoryDelete() {
        when(repository.findById(id)).thenReturn(Optional.of(manager));
        doNothing().when(repository).delete(manager);

        assertDoesNotThrow(() -> service.delete(id));

        verify(repository, times(1)).delete(manager);
    }
}