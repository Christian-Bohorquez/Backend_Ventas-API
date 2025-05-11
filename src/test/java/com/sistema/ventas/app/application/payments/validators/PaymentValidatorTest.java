package com.sistema.ventas.app.application.payments.validators;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentValidatorTest {
    private final IPaymentRepositoryPort repository = mock(IPaymentRepositoryPort.class);
    private final PaymentValidator validator = new PaymentValidator(repository);

    @Test
    void validateCreate_NameExists_ThrowsException() {
        // GIVEN
        Payment payment = new Payment("TestPayment");
        when(repository.getByName("TestPayment")).thenReturn(Optional.of(payment));

        // WHEN & THEN
        try {
            UniqueConstraintViolationException exception = assertThrows(
                    UniqueConstraintViolationException.class,
                    () -> validator.validateCreate(payment)
            );
            // Ajusta el mensaje esperado para coincidir con el mensaje real
            assertEquals("El nombre ya está registrado", exception.getMessage());
            verify(repository, times(1)).getByName("TestPayment");
            System.out.println("Prueba exitosa: Se detectó correctamente un nombre ya existente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_NameExists_ThrowsException: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void validateCreate_NameDoesNotExist_Success() {
        // GIVEN
        Payment payment = new Payment("TestPayment");
        when(repository.getByName("TestPayment")).thenReturn(Optional.empty());

        // WHEN & THEN
        try {
            assertDoesNotThrow(() -> validator.validateCreate(payment));
            verify(repository, times(1)).getByName("TestPayment");
            System.out.println("Prueba exitosa: El nombre no existe y se permitió crear el pago.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_NameDoesNotExist_Success: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateGetById_IdDoesNotExist_ThrowsException() {
        // GIVEN
        UUID id = UUID.randomUUID();
        when(repository.getById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        try {
            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> validator.validateGetById(id)
            );
            // Ajusta el mensaje esperado para coincidir con el mensaje real
            assertEquals("No se encontró el/la Forma de pago.", exception.getMessage());
            verify(repository, times(1)).getById(id);
            System.out.println("Prueba exitosa: Se detectó correctamente un ID inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateGetById_IdDoesNotExist_ThrowsException: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void validateGetById_IdExists_Success() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Payment payment = new Payment(id, "TestPayment");
        when(repository.getById(id)).thenReturn(Optional.of(payment));

        // WHEN & THEN
        try {
            assertDoesNotThrow(() -> validator.validateGetById(id));
            verify(repository, times(1)).getById(id);
            System.out.println("Prueba exitosa: El ID existe y fue validado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateGetById_IdExists_Success: " + e.getMessage());
            throw e;
        }
    }
}
