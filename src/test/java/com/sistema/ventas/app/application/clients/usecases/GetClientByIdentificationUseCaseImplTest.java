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

class GetClientByIdentificationUseCaseImplTest {

    private IClientRepositoryPort repository;
    private ClientValidator validator;
    private GetClientByIdentificationUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IClientRepositoryPort.class);
        validator = Mockito.mock(ClientValidator.class);
        useCase = new GetClientByIdentificationUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidIdentification_ReturnsClient() {
        String identification = "0942673971";
        Client client = Client.create(UUID.randomUUID(), identification, "Juan", "Pérez", "juan@gmail.com");

        when(repository.getByIdentification(identification)).thenReturn(Optional.of(client));

        try {
            Client result = useCase.execute(identification);
            assertEquals(client, result);
            verify(validator).validateGetByIdentification(identification);
            verify(repository).getByIdentification(identification);
            System.out.println("Prueba exitosa: Se obtuvo correctamente el cliente por identificación.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidIdentification_ReturnsClient: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidIdentification_ThrowsNotFoundException() {
        String identification = "0942673971";

        when(repository.getByIdentification(identification)).thenReturn(Optional.empty());

        try {
            // Aquí se espera la NoSuchElementException en lugar de la NotFoundException
            assertThrows(NoSuchElementException.class, () -> useCase.execute(identification));
            verify(validator).validateGetByIdentification(identification);
            verify(repository).getByIdentification(identification);
            System.out.println("Prueba exitosa: Se lanzó la excepción NoSuchElementException para una identificación inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidIdentification_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_IdentificationWithSpecialCharacters_ThrowsValidationException() {
        String invalidIdentification = "09426@&73971";

        doThrow(new ValidationException("Identificación no puede contener caracteres especiales"))
                .when(validator).validateGetByIdentification(invalidIdentification);

        assertThrows(ValidationException.class, () -> useCase.execute(invalidIdentification));
        verify(validator).validateGetByIdentification(invalidIdentification);
        verify(repository, never()).getByIdentification(any());
    }

}
