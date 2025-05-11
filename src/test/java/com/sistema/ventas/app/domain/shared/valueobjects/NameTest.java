package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("Debe crear un nombre válido")
    void shouldCreateValidName() {
        Name name = Name.create("Juan", "nombres");
        assertNotNull(name);
        assertEquals("Juan", name.getValue());

        Name fullName = Name.create("Juan Pérez", "nombres");
        assertNotNull(fullName);
        assertEquals("Juan Pérez", fullName.getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre es nulo o vacío")
    void shouldThrowExceptionWhenNameIsNullOrEmpty() {
        assertThrows(RequiredFieldMissingException.class, () -> Name.create(null, "nombres"));
        assertThrows(RequiredFieldMissingException.class, () -> Name.create("", "nombres"));
        assertThrows(RequiredFieldMissingException.class, () -> Name.create("   ", "nombres"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre contiene números")
    void shouldThrowExceptionWhenNameContainsNumbers() {
        assertThrows(ValueObjectValidationException.class, () -> Name.create("Juan123", "nombres"));
        assertThrows(ValueObjectValidationException.class, () -> Name.create("Pérez1", "apellidos"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre tiene más de dos palabras")
    void shouldThrowExceptionWhenNameHasMoreThanTwoWords() {
        assertThrows(ValueObjectValidationException.class, () -> Name.create("Juan Pérez Martínez", "nombres"));
        assertThrows(ValueObjectValidationException.class, () -> Name.create("Carlos Eduardo Pérez", "apellidos"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre contiene caracteres especiales")
    void shouldThrowExceptionWhenNameContainsSpecialCharacters() {
        assertThrows(ValueObjectValidationException.class, () -> Name.create("Juan#Pérez", "nombres"));
        assertThrows(ValueObjectValidationException.class, () -> Name.create("@Pérez", "apellidos"));
    }
}
