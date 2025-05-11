package com.sistema.ventas.app.application.clients.validators;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.persons.port.out.IPersonRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientValidatorTest {

    private IPersonRepositoryPort personRepository;
    private IClientRepositoryPort clientRepository;
    private ClientValidator validator;

    @BeforeEach
    void setup() {
        personRepository = Mockito.mock(IPersonRepositoryPort.class);
        clientRepository = Mockito.mock(IClientRepositoryPort.class);
        validator = new ClientValidator(personRepository, clientRepository);

    }

    @Test
    void validateCreate_ClientWithExistingIdentification_ThrowsUniqueConstraintViolationException() {
        String identification = "0942673971"; // Identificación válida
        Client client = Client.create(null, identification, "Juan", "Pérez", "juan@gmail.com");

        when(personRepository.existsByIdentification(identification)).thenReturn(true);

        UniqueConstraintViolationException exception = assertThrows(
                UniqueConstraintViolationException.class,
                () -> validator.validateCreate(client)
        );

        assertEquals("identificación ya está registrado", exception.getMessage());
        verify(personRepository).existsByIdentification(identification);
        verify(clientRepository, never()).getByEmail(anyString());
        System.out.println("Prueba exitosa: validación de identificación duplicada.");
    }


    @Test
    void validateCreate_ClientWithExistingEmail_ThrowsUniqueConstraintViolationException() {
        String email = "juan@gmail.com";
        Client client = Client.create(null, "0942673971", "Juan", "Pérez", email);

        when(personRepository.existsByIdentification(client.getIdentification().getValue())).thenReturn(false);
        when(clientRepository.getByEmail(email)).thenReturn(Optional.of(client));

        UniqueConstraintViolationException exception = assertThrows(
                UniqueConstraintViolationException.class,
                () -> validator.validateCreate(client)
        );

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(personRepository).existsByIdentification(client.getIdentification().getValue());
        verify(clientRepository).getByEmail(email);
        System.out.println("Prueba exitosa: validación de correo electrónico duplicado.");
    }

    @Test
    void validateCreate_ValidClient_PassesValidation() {
        String email = "juan@gmail.com";
        String identification = "0942673971"; // Identificación válida
        Client client = Client.create(null, identification, "Juan", "Pérez", email);

        when(personRepository.existsByIdentification(identification)).thenReturn(false);
        when(clientRepository.getByEmail(email)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validateCreate(client));

        verify(personRepository).existsByIdentification(identification);
        verify(clientRepository).getByEmail(email);
        System.out.println("Prueba exitosa: cliente válido creado correctamente.");
    }



    @Test
    void validateGetById_ClientNotFound_ThrowsNotFoundException() {
        UUID clientId = UUID.randomUUID();

        when(clientRepository.getById(clientId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> validator.validateGetById(clientId)
        );

        assertEquals("No se encontró el/la Cliente.", exception.getMessage());
        verify(clientRepository).getById(clientId);
        System.out.println("Prueba exitosa: validación de cliente inexistente por ID.");
    }

    @Test
    void validateGetById_ClientExists_PassesValidation() {
        UUID clientId = UUID.randomUUID();
        Client client = Client.create(clientId, "0942673971", "Juan", "Pérez", "juan@gmail.com");

        when(clientRepository.getById(clientId)).thenReturn(Optional.of(client));

        assertDoesNotThrow(() -> validator.validateGetById(clientId));

        verify(clientRepository).getById(clientId);
        System.out.println("Prueba exitosa: cliente encontrado por ID.");
    }

    @Test
    void validateGetByIdentification_ClientNotFound_ThrowsNotFoundException() {
        String identification = "0942673971"; // Identificación válida

        when(personRepository.existsByIdentification(identification)).thenReturn(false);

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> validator.validateGetByIdentification(identification)
        );

        assertEquals("No se encontró el/la cliente.", exception.getMessage());
        verify(personRepository).existsByIdentification(identification);
        System.out.println("Prueba exitosa: validación de identificación inexistente.");
    }


    @Test
    void validateUpdate_EmailAlreadyUsedByAnotherClient_ThrowsUniqueConstraintViolationException() {
        UUID clientId = UUID.randomUUID();
        Client clientToUpdate = Client.create(clientId, "0942673971", "Juan", "Pérez", "juan@gmail.com");
        Client otherClient = Client.create(UUID.randomUUID(), "0951618024", "Carlos", "Gómez", "juaGn@gmail.com");

        when(clientRepository.getById(clientId)).thenReturn(Optional.of(clientToUpdate));
        when(clientRepository.getByEmail("juan@gmail.com")).thenReturn(Optional.of(otherClient));

        UniqueConstraintViolationException exception = assertThrows(
                UniqueConstraintViolationException.class,
                () -> validator.validateUpdate(clientToUpdate)
        );

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(clientRepository).getById(clientId);
        verify(clientRepository).getByEmail("juan@gmail.com");

        System.out.println("Prueba exitosa: validación de email duplicado en actualización.");
    }

    @Test
    void validateUpdate_ValidUpdate_PassesValidation() {
        UUID clientId = UUID.randomUUID();
        Client client = Client.create(clientId, "0942673971", "Juan", "Pérez", "juan@gmail.com");

        when(clientRepository.getById(clientId)).thenReturn(Optional.of(client));
        when(clientRepository.getByEmail(client.getEmail().getValue())).thenReturn(Optional.of(client));

        assertDoesNotThrow(() -> validator.validateUpdate(client));

        verify(clientRepository).getById(clientId);
        verify(clientRepository).getByEmail(client.getEmail().getValue());
        System.out.println("Prueba exitosa: cliente actualizado correctamente.");
    }
}
