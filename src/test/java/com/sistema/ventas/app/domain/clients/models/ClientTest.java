package com.sistema.ventas.app.domain.clients.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    @DisplayName("Debe crear un cliente válido con datos correctos")
    void shouldCreateValidClient() {
        Client client = Client.create(
                UUID.randomUUID(),
                "0951618024",
                "Juan",
                "Perez",
                "juan.perez@gmail.com"
        );

        assertNotNull(client);
        assertEquals("0951618024", client.getIdentification().getValue());
        assertEquals("Juan", client.getFirstName().getValue());
        assertEquals("Perez", client.getLastName().getValue());
        assertEquals("juan.perez@gmail.com", client.getEmail().getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la identificación es inválida")
    void shouldThrowExceptionForInvalidIdentification() {
        assertThrows(ValueObjectValidationException.class, () ->
                Client.create(UUID.randomUUID(), "123", "Juan", "Perez", "juan.perez@gmail.com")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si el primer nombre es nulo o vacío")
    void shouldThrowExceptionForInvalidFirstName() {
        assertThrows(RequiredFieldMissingException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", "", "Perez", "juan.perez@gmail.com")
        );

        assertThrows(RequiredFieldMissingException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", null, "Perez", "juan.perez@gmail.com")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si el apellido es nulo o vacío")
    void shouldThrowExceptionForInvalidLastName() {
        assertThrows(RequiredFieldMissingException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", "Juan", "", "juan.perez@gmail.com")
        );

        assertThrows(RequiredFieldMissingException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", "Juan", null, "juan.perez@gmail.com")
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo tiene formato inválido")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(ValueObjectValidationException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", "Juan", "Perez", "juan.perez@invalid")
        );

        assertThrows(ValueObjectValidationException.class, () ->
                Client.create(UUID.randomUUID(), "0951618024", "Juan", "Perez", "juan.perezgmail.com")
        );
    }

    @Test
    @DisplayName("Debe actualizar el correo electrónico con un email válido")
    void shouldUpdateEmailSuccessfully() {
        Client client = Client.create(
                UUID.randomUUID(),
                "0951618024",
                "Juan",
                "Perez",
                "juan.perez@gmail.com"
        );

        client.UpdateEmail("nuevo.correo@hotmail.com");

        assertEquals("nuevo.correo@hotmail.com", client.getEmail().getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nuevo correo es inválido")
    void shouldThrowExceptionWhenUpdatingWithInvalidEmail() {
        Client client = Client.create(
                UUID.randomUUID(),
                "0951618024",
                "Juan",
                "Perez",
                "juan.perez@gmail.com"
        );

        assertThrows(ValueObjectValidationException.class, () -> client.UpdateEmail("correo-invalido"));
    }
}
