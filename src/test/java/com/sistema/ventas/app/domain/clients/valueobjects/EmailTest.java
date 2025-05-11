package com.sistema.ventas.app.domain.clients.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("Debe crear un correo válido con formato correcto")
    void shouldCreateValidEmail() {
        Email email = Email.create("juan.perez@gmail.com");
        assertNotNull(email);
        assertEquals("juan.perez@gmail.com", email.getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo es nulo o vacío")
    void shouldThrowExceptionWhenEmailIsNullOrEmpty() {
        assertThrows(ValueObjectValidationException.class, () -> Email.create(null));
        assertThrows(ValueObjectValidationException.class, () -> Email.create(""));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo tiene espacios en blanco internos o externos")
    void shouldThrowExceptionWhenEmailHasSpaces() {
        assertThrows(ValueObjectValidationException.class, () -> Email.create(" juan.perez@gmail.com ")); // Espacios externos
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan. perez@gmail.com")); // Espacio interno
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan.perez @gmail.com")); // Espacio antes del @
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo tiene formato inválido")
    void shouldThrowExceptionWhenEmailHasInvalidFormat() {
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan.perezgmail.com"));
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan.perez@com"));
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan.perez@gmail"));
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan.perez@"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo contiene caracteres no permitidos")
    void shouldThrowExceptionWhenEmailHasInvalidCharacters() {
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan perez@gmail.com"));
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan*perez@gmail.com"));
        assertThrows(ValueObjectValidationException.class, () -> Email.create("juan&%$perez@gmail.com"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo no pertenece a dominios permitidos")
    void shouldThrowExceptionWhenEmailHasUnsupportedDomain() {
        assertThrows(ValueObjectValidationException.class, () ->
                Email.create("user@tempmail.com")
        );
    }

    @Test
    @DisplayName("Debe aceptar un email con el tamaño mínimo permitido")
    void shouldAcceptEmailWithMinimumLength() {
        Email email = Email.create("a@b.co"); // Ahora válido
        assertNotNull(email);
        assertEquals("a@b.co", email.getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo excede el tamaño máximo permitido")
    void shouldThrowExceptionWhenEmailIsTooLong() {
        String longEmail = "juan.perez".repeat(10) + "@gmail.com"; // Email demasiado largo
        assertThrows(ValueObjectValidationException.class, () -> Email.create(longEmail));
    }
}
