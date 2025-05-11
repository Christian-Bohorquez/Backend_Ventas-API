package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateClientUseCaseImplTest {

    private IClientRepositoryPort repository;
    private ClientValidator validator;
    private UpdateClientUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        validator = Mockito.mock(ClientValidator.class);
        useCase = new UpdateClientUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidClient_UpdatesClient() {
        Client client = Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "juan@gmail.com");

        try {
            useCase.execute(client);
            verify(validator).validateUpdate(client);
            verify(repository).save(client);
            System.out.println("Prueba exitosa: El cliente fue actualizado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidClient_UpdatesClient: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidClient_ThrowsNotFoundException() {
        Client client = Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "juan@gmail.com");

        doThrow(new NotFoundException("Cliente")).when(validator).validateUpdate(client);

        try {
            assertThrows(NotFoundException.class, () -> useCase.execute(client));
            verify(validator).validateUpdate(client);
            verify(repository, never()).save(client);
            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para cliente inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidClient_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }


}
