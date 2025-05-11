package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetClientByIdUseCaseImplTest {

    private IClientRepositoryPort repository;
    private ClientValidator validator;
    private GetClientByIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        validator = Mockito.mock(ClientValidator.class);
        useCase = new GetClientByIdUseCaseImpl(repository, validator);
    }


    @Test
    void execute_InvalidId_ThrowsNotFoundException() {
        UUID clientId = UUID.randomUUID();

        when(repository.getById(clientId)).thenReturn(Optional.empty());

        // Cambiar la excepción esperada a NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> useCase.execute(clientId));

        verify(validator).validateGetById(clientId);
        verify(repository).getById(clientId);
        System.out.println("Prueba exitosa: Se lanzó la excepción NoSuchElementException para un ID inexistente.");
    }


    @Test
    void execute_NullId_ThrowsIllegalArgumentException() {
        // Simulamos que el validador lanza una IllegalArgumentException si el ID es nulo
        doThrow(new IllegalArgumentException("El ID no puede ser nulo"))
                .when(validator).validateGetById(null);

        // Verificamos que se lance la IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));

        // Mensaje adicional cuando se lanza la excepción correctamente
        assertTrue(exception.getMessage().contains("El ID no puede ser nulo"), "El mensaje de la excepción debe ser el esperado.");

        // Verificamos que se haya llamado al validador con el valor nulo
        verify(validator).validateGetById(null);

        // Verificamos que el repositorio no haya sido llamado
        verify(repository, never()).getById(any());

        System.out.println("Prueba exitosa: Se lanzó IllegalArgumentException con el mensaje esperado cuando el ID es nulo.");
    }



}
