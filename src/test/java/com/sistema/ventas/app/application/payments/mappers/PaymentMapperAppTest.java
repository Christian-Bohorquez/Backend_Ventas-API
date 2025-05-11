package com.sistema.ventas.app.application.payments.mappers;

import com.sistema.ventas.app.application.payments.dtos.PaymentCreateDto;
import com.sistema.ventas.app.application.payments.dtos.PaymentGetDto;
import com.sistema.ventas.app.domain.payments.models.Payment;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperAppTest {

    private final PaymentMapperApp mapper = new PaymentMapperApp();

    @Test
    void toPayment_ValidPaymentCreateDto_ReturnsPayment() throws Exception {
        // GIVEN
        PaymentCreateDto dto = new PaymentCreateDto();

        // Usamos reflexión para establecer el valor del campo 'name'
        Field nameField = PaymentCreateDto.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(dto, "TestPayment");

        // WHEN
        Payment payment = mapper.toPayment(dto);

        // THEN
        try {
            assertNotNull(payment, "El objeto Payment no debe ser null");
            assertEquals("TestPayment", payment.getName(), "El nombre no coincide");
            assertNotNull(payment.getId(), "El ID no debe ser null");
            System.out.println("Prueba exitosa: El objeto Payment fue mapeado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba: " + e.getMessage());
            throw e; // Relanzamos el error para que JUnit registre el fallo
        }
    }

    @Test
    void toDtoGet_ValidPayment_ReturnsPaymentGetDto() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, "TestPayment");

        // WHEN
        PaymentGetDto dto = mapper.toDtoGet(payment);

        // THEN
        try {
            assertNotNull(dto, "El objeto PaymentGetDto no debe ser null");
            assertEquals(payment.getId(), dto.getId(), "El ID no coincide entre Payment y PaymentGetDto");
            assertEquals(payment.getName(), dto.getName(), "El nombre no coincide entre Payment y PaymentGetDto");
            System.out.println("Prueba exitosa: El objeto PaymentGetDto fue mapeado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toDtoGet_ValidPayment_ReturnsPaymentGetDto: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void toDtoGet_NullPayment_ThrowsException() {
        // GIVEN
        Payment payment = null;

        // WHEN & THEN
        try {
            assertThrows(
                    NullPointerException.class,
                    () -> mapper.toDtoGet(payment),
                    "Debería lanzar una excepción si el Payment es null"
            );
            System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un Payment null.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toDtoGet_NullPayment_ThrowsException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void toPayment_NullPaymentCreateDto_ThrowsException() {
        // GIVEN
        PaymentCreateDto dto = null;

        // WHEN & THEN
        try {
            assertThrows(
                    NullPointerException.class,
                    () -> mapper.toPayment(dto),
                    "Debería lanzar una excepción si el PaymentCreateDto es null"
            );
            System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un PaymentCreateDto null.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toPayment_NullPaymentCreateDto_ThrowsException: " + e.getMessage());
            throw e;
        }
    }
}