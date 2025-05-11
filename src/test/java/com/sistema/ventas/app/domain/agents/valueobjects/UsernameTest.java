package com.sistema.ventas.app.domain.agents.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class UsernameTest {

    @ParameterizedTest
    @CsvSource({
            ", , RequiredFieldMissingException",         // Username nulo
            "'   ', , RequiredFieldMissingException",   // Username vacío
            "'Ab1', , ValueObjectValidationException",  // Menos de 4 caracteres
            "'Abc12345678901234567890', , ValueObjectValidationException", // Más de 20 caracteres
            "'abc123', , ValueObjectValidationException", // Sin mayúscula
            "'ABC123', , ValueObjectValidationException", // Sin minúscula
            "'Juan123', 'Juan Perez', ValueObjectValidationException" // Contiene nombre completo
    })
    @DisplayName("Debe fallar con nombres de usuario inválidos")
    void shouldThrowExceptionForInvalidUsernames(String username, String fullName, String expectedException) {
        if ("RequiredFieldMissingException".equals(expectedException)) {
            assertThrows(RequiredFieldMissingException.class, () -> Username.create(username, fullName));
        } else {
            assertThrows(ValueObjectValidationException.class, () -> Username.create(username, fullName));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "'Abc123', 'Juan Perez'",
            "'Xyz987', 'Maria Lopez'",
            "'Secure123', 'Carlos Ramirez'"
    })
    @DisplayName("Debe crear nombres de usuario válidos")
    void shouldCreateValidUsernames(String username, String fullName) {
        Username validUsername = Username.create(username, fullName);
        assertNotNull(validUsername);
        assertEquals(username, validUsername.getValue());
    }
}
