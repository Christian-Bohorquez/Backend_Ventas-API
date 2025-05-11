package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateClientUseCaseImplTest {

    private IClientRepositoryPort repository;
    private ClientValidator validator;
    private CreateClientUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        validator = Mockito.mock(ClientValidator.class);
        useCase = new CreateClientUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidClient_CreatesClient() {
        Client client = Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "juan@gmail.com");

        try {
            useCase.execute(client);
            verify(validator).validateCreate(client);
            verify(repository).save(client);
            System.out.println("Prueba exitosa: Se creó correctamente el cliente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidClient_CreatesClient: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidClient_ThrowsException() {
        Client client = Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "juan@gmail.com");

        doThrow(new UniqueConstraintViolationException("identificación"))
                .when(validator).validateCreate(client);

        try {
            assertThrows(UniqueConstraintViolationException.class, () -> useCase.execute(client));
            verify(validator).validateCreate(client);
            verify(repository, never()).save(client);
            System.out.println("Prueba exitosa: Se lanzó la excepción por una violación de restricción única.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidClient_ThrowsException: " + e.getMessage());
            throw e;
        }
    }
    @Test
    void execute_InvalidEmailFormat_ThrowsValidationException() {
        try {
            // Intentamos crear el cliente con un correo inválido
            assertThrows(ValueObjectValidationException.class, () -> {
                Client client = Client.create(UUID.randomUUID(), "0942673971", "Juan", "Pérez", "invalid-email");
            });
            System.out.println("Prueba exitosa: formato inválido de correo electrónico.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidEmailFormat_ThrowsValidationException: " + e.getMessage());
            throw e;
        }
    }

}
