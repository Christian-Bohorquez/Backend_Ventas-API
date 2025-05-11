package com.sistema.ventas.app.domain.roles.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("✅ Debe crear un Role válido con un nombre y generar automáticamente un ID")
    void shouldCreateValidRoleWithGeneratedId() {
        Role role = new Role("Admin");

        assertNotNull(role.getId(), "El ID no debería ser nulo");
        assertEquals("Admin", role.getName(), "El nombre debería coincidir");
    }

    @Test
    @DisplayName("✅ Debe crear un Role válido con un ID y un nombre")
    void shouldCreateValidRoleWithProvidedId() {
        UUID id = UUID.randomUUID();
        Role role = new Role(id, "User");

        assertEquals(id, role.getId(), "El ID debe coincidir");
        assertEquals("User", role.getName(), "El nombre debe coincidir");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    @DisplayName("❌ Debe lanzar excepción si el nombre es nulo o vacío (constructor con nombre)")
    void shouldThrowExceptionWhenNameIsInvalidWithSingleArgConstructor(String invalidName) {
        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Role(invalidName));

        assertEquals("El campo 'nombre' es obligatorio", exception.getMessage(), "Mensaje incorrecto en excepción");
    }

    @Test
    @DisplayName("❌ Debe lanzar excepción si el ID es nulo (constructor con ID y nombre)")
    void shouldThrowExceptionWhenIdIsNull() {
        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Role(null, "Admin"));

        assertEquals("El campo 'id' es obligatorio", exception.getMessage(), "Mensaje incorrecto en excepción");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    @DisplayName("❌ Debe lanzar excepción si el ID es válido pero el nombre es nulo o vacío")
    void shouldThrowExceptionWhenIdIsValidButNameIsInvalid(String invalidName) {
        UUID validId = UUID.randomUUID();

        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Role(validId, invalidName));

        assertEquals("El campo 'nombre' es obligatorio", exception.getMessage(), "Mensaje incorrecto en excepción");
    }

    @Test
    @DisplayName("🔄 Debe verificar que un Role válido mantiene sus valores después de la creación")
    void shouldMaintainValuesAfterCreation() {
        UUID id = UUID.randomUUID();
        String name = "Moderador";
        Role role = new Role(id, name);

        assertEquals(id, role.getId(), "El ID debe mantenerse igual");
        assertEquals(name, role.getName(), "El nombre debe mantenerse igual");
    }
}
