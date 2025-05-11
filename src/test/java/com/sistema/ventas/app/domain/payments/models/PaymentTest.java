package com.sistema.ventas.app.domain.payments.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    @DisplayName("✅ Debe crear un objeto Payment válido con ID generado automáticamente")
    void shouldCreateValidPaymentWithGeneratedId() {
        Payment payment = new Payment("Test Payment");

        assertNotNull(payment.getId(), "El ID generado no debería ser nulo");
        assertEquals("Test Payment", payment.getName(), "El nombre debería coincidir");
    }

    @Test
    @DisplayName("✅ Debe crear un objeto Payment válido con ID proporcionado")
    void shouldCreateValidPaymentWithProvidedId() {
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, "Test Payment");

        assertEquals(id, payment.getId(), "El ID proporcionado debe coincidir");
        assertEquals("Test Payment", payment.getName(), "El nombre debe coincidir");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    @DisplayName("❌ Debe lanzar excepción si el nombre es nulo o vacío")
    void shouldThrowExceptionWhenNameIsNullOrEmpty(String invalidName) {
        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Payment(invalidName));

        assertEquals("El campo 'nombre' es obligatorio", exception.getMessage(), "El mensaje de error debe coincidir");
    }

    @Test
    @DisplayName("❌ Debe lanzar excepción si el ID es nulo")
    void shouldThrowExceptionWhenIdIsNull() {
        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Payment(null, "Test Payment"));

        assertEquals("El campo 'id' es obligatorio", exception.getMessage(), "El mensaje de error debe coincidir");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    @DisplayName("❌ Debe lanzar excepción si el ID es válido pero el nombre es nulo o vacío")
    void shouldThrowExceptionWhenIdIsValidButNameIsInvalid(String invalidName) {
        UUID validId = UUID.randomUUID();

        RequiredFieldMissingException exception =
                assertThrows(RequiredFieldMissingException.class, () -> new Payment(validId, invalidName));

        assertEquals("El campo 'nombre' es obligatorio", exception.getMessage(), "El mensaje de error debe coincidir");
    }

    @Test
    @DisplayName("🔄 Debe verificar que un Payment válido mantiene sus valores después de la creación")
    void shouldMaintainValuesAfterCreation() {
        UUID id = UUID.randomUUID();
        String name = "Compra de Producto";
        Payment payment = new Payment(id, name);

        assertEquals(id, payment.getId(), "El ID debe mantenerse igual");
        assertEquals(name, payment.getName(), "El nombre debe mantenerse igual");
    }
}
