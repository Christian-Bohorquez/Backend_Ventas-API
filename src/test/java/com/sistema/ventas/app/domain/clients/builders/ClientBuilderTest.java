package com.sistema.ventas.app.domain.clients.builders;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientBuilderTest {

    @Test
    @DisplayName("Debe construir un objeto Client válido con todos los datos correctos")
    void shouldBuildValidClient() {
        Client client = new ClientBuilder()
                .withId(UUID.randomUUID())
                .withIdentification("0951618024")
                .withFirstName("Juan")
                .withLastName("Perez")
                .withEmail("juan.perez@gmail.com")
                .build();

        assertNotNull(client);
        assertEquals("0951618024", client.getIdentification().getValue());
        assertEquals("Juan", client.getFirstName().getValue());
        assertEquals("Perez", client.getLastName().getValue());
        assertEquals("juan.perez@gmail.com", client.getEmail().getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si algún campo obligatorio es nulo")
    void shouldThrowExceptionForNullFields() {
        assertThrows(RequiredFieldMissingException.class, () ->
                new ClientBuilder().withId(UUID.randomUUID()).build()
        );

        assertThrows(RequiredFieldMissingException.class, () ->
                new ClientBuilder().withFirstName("Juan").build()
        );

        assertThrows(RequiredFieldMissingException.class, () ->
                new ClientBuilder().withLastName("Perez").build()
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si la identificación es inválida")
    void shouldThrowExceptionForInvalidIdentification() {
        assertThrows(ValueObjectValidationException.class, () ->
                new ClientBuilder().withIdentification("12").build()
        );

        assertThrows(ValueObjectValidationException.class, () ->
                new ClientBuilder().withIdentification("ABCDEFGHIJ").build()
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si el email tiene formato incorrecto")
    void shouldThrowExceptionForInvalidEmail() {
        ClientBuilder builder = new ClientBuilder()
                .withId(UUID.randomUUID())
                .withIdentification("0951618024") // Se agrega identificación válida
                .withFirstName("Juan")
                .withLastName("Perez")
                .withEmail("correo-invalido"); // Email inválido

        assertThrows(ValueObjectValidationException.class, builder::build);
    }


    @Test
    @DisplayName("Debe generar un ID automáticamente si es nulo")
    void shouldGenerateIdWhenIdIsNull() {
        Client client = new ClientBuilder()
                .withId(null) // ID nulo
                .withIdentification("0951618024")
                .withFirstName("Juan")
                .withLastName("Perez")
                .withEmail("juan.perez@gmail.com")
                .build();

        assertNotNull(client.getId());
    }

    @Test
    @DisplayName("Debe manejar correctamente múltiples errores combinados")
    void shouldThrowExceptionForMultipleErrors() {
        ClientBuilder builder = new ClientBuilder()
                .withIdentification("") // Identificación inválida
                .withFirstName("") // Nombre inválido
                .withLastName("") // Apellido inválido
                .withEmail("correo-invalido"); // Email inválido

        assertThrows(RequiredFieldMissingException.class, builder::build);
    }
}
