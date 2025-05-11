package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAllClientsUseCaseImplTest {

    private IClientRepositoryPort repository;
    private GetAllClientsUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        useCase = new GetAllClientsUseCaseImpl(repository);
    }

    @Test
    void execute_ReturnsListOfClients() {
        List<Client> clients = List.of(
                Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "juan@gmail.com"),
                Client.create(UUID.randomUUID(), "0951618024", "María", "Gómez", "maria@gmail.com")
        );

        when(repository.getAll()).thenReturn(clients);

        try {
            List<Client> result = useCase.execute();
            assertEquals(clients, result);
            verify(repository).getAll();
            System.out.println("Prueba exitosa: Se obtuvieron correctamente todos los clientes.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ReturnsListOfClients: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_NoClients_ReturnsEmptyList() {
        when(repository.getAll()).thenReturn(new ArrayList<>());

        try {
            List<Client> result = useCase.execute();
            assertEquals(0, result.size());
            verify(repository).getAll();
            System.out.println("Prueba exitosa: Se validó correctamente que no hay clientes disponibles.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_NoClients_ReturnsEmptyList: " + e.getMessage());
            throw e;
        }
    }
    @Test
    void execute_RepositoryThrowsException_ThrowsRuntimeException() {
        when(repository.getAll()).thenThrow(new RuntimeException("Error de conexión a la base de datos"));

        assertThrows(RuntimeException.class, () -> useCase.execute());
        verify(repository).getAll();
    }

}
