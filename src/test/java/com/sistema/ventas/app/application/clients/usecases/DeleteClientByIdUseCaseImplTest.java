package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteClientByIdUseCaseImplTest {

    private IClientRepositoryPort repository;
    private ClientValidator validator;
    private DeleteClientByIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        validator = Mockito.mock(ClientValidator.class);
        useCase = new DeleteClientByIdUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidId_DeletesClient() {
        UUID clientId = UUID.randomUUID();

        try {
            useCase.execute(clientId);
            verify(validator).validateGetById(clientId);
            verify(repository).delete(clientId);
            System.out.println("Prueba exitosa: El cliente fue eliminado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidId_DeletesClient: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidId_ThrowsNotFoundException() {
        UUID clientId = UUID.randomUUID();

        doThrow(new NotFoundException("Cliente")).when(validator).validateGetById(clientId);

        try {
            assertThrows(NotFoundException.class, () -> useCase.execute(clientId));
            verify(validator).validateGetById(clientId);
            verify(repository, never()).delete(clientId);
            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para un ID inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidId_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }
    @Test
    void execute_DeletingNonExistingClient_ThrowsNotFoundException() {
        UUID clientId = UUID.randomUUID();
        doThrow(new NotFoundException("Cliente no encontrado")).when(validator).validateGetById(clientId);

        assertThrows(NotFoundException.class, () -> useCase.execute(clientId));
        verify(validator).validateGetById(clientId);
        verify(repository, never()).delete(clientId);
    }

}
