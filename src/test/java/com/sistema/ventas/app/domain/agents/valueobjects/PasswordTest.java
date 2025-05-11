package com.sistema.ventas.app.domain.agents.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @ParameterizedTest
    @CsvSource({
            ", RequiredFieldMissingException",         // Password nula
            "'   ', RequiredFieldMissingException",   // Password vacía
            "'Ab1@5', ValueObjectValidationException",// Menos de 8 caracteres
            "'Abc12345@123456789', ValueObjectValidationException", // Más de 15 caracteres
            "'abc12345@', ValueObjectValidationException", // Sin mayúscula
            "'Abc123456', ValueObjectValidationException"  // Sin caracter especial
    })
    @DisplayName("Debe fallar con contraseñas inválidas")
    void shouldThrowExceptionForInvalidPasswords(String password, String expectedException) {
        if ("RequiredFieldMissingException".equals(expectedException)) {
            assertThrows(RequiredFieldMissingException.class, () -> Password.create(password));
        } else {
            assertThrows(ValueObjectValidationException.class, () -> Password.create(password));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "'Abc12345@'", // Password válida
            "'Xyz98765!'", // Otra password válida
            "'Secure1*'"   // Otra válida
    })
    @DisplayName("Debe crear contraseñas válidas")
    void shouldCreateValidPassword(String password) {
        Password validPassword = Password.create(password);
        assertNotNull(validPassword);
        assertEquals(password, validPassword.getValue());
    }
}
