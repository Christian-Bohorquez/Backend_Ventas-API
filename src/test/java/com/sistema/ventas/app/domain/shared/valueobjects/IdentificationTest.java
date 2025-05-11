package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentificationTest {

    @Test
    @DisplayName("Debe crear una identificación válida")
    void shouldCreateValidIdentification() {
        Identification id = Identification.create("0951618024");

        assertNotNull(id);
        assertEquals("0951618024", id.getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el valor es nulo o vacío")
    void shouldThrowExceptionWhenValueIsNullOrEmpty() {
        assertThrows(RequiredFieldMissingException.class, () -> Identification.create(null));
        assertThrows(RequiredFieldMissingException.class, () -> Identification.create(""));
        assertThrows(RequiredFieldMissingException.class, () -> Identification.create("   "));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el valor contiene caracteres no numéricos")
    void shouldThrowExceptionWhenValueContainsNonNumericCharacters() {
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("12345abcd"));
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("12345#6789"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el valor tiene menos o más de 10 dígitos")
    void shouldThrowExceptionWhenValueHasInvalidLength() {
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("123"));
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("123456789012"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el valor tiene un dígito verificador incorrecto")
    void shouldThrowExceptionWhenValueHasInvalidChecksum() {
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("0951618025")); // Dígito incorrecto
    }

    @Test
    @DisplayName("Debe lanzar excepción si el código de provincia no es válido")
    void shouldThrowExceptionWhenProvinceCodeIsInvalid() {
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("3000000009")); // Código inválido
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("0000000001")); // Código inválido
    }
}
