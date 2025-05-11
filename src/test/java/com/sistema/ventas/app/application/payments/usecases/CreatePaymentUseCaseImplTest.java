package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.application.payments.validators.PaymentValidator;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreatePaymentUseCaseImplTest {

    private IPaymentRepositoryPort repository;
    private PaymentValidator validator;
    private CreatePaymentUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IPaymentRepositoryPort.class);
        validator = Mockito.mock(PaymentValidator.class);
        useCase = new CreatePaymentUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidPayment_SavesPayment() {
        Payment payment = new Payment(UUID.randomUUID(), "Nuevo Pago");

        try {
            useCase.execute(payment);
            verify(validator).validateCreate(payment);
            verify(repository).save(payment);
            System.out.println("Prueba exitosa: Se validó y guardó correctamente el pago.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidPayment_SavesPayment: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidPayment_ThrowsException() {
        Payment payment = new Payment(UUID.randomUUID(), "Pago Existente");
        doThrow(new RuntimeException("El nombre ya está registrado.")).when(validator).validateCreate(payment);

        try {
            Exception exception = assertThrows(RuntimeException.class, () -> useCase.execute(payment));
            assertEquals("El nombre ya está registrado.", exception.getMessage());
            verify(validator).validateCreate(payment);
            verify(repository, never()).save(payment);
            System.out.println("Prueba exitosa: Se detectó correctamente un nombre ya registrado.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidPayment_ThrowsException: " + e.getMessage());
            throw e;
        }
    }


}
