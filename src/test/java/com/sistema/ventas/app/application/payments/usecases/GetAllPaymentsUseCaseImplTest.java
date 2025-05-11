package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetAllPaymentsUseCaseImplTest {

    private IPaymentRepositoryPort repository;
    private GetAllPaymentsUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IPaymentRepositoryPort.class);
        useCase = new GetAllPaymentsUseCaseImpl(repository);
    }

    @Test
    void execute_ReturnsListOfPayments() {
        List<Payment> payments = List.of(
                new Payment(UUID.randomUUID(), "Pago 1"),
                new Payment(UUID.randomUUID(), "Pago 2")
        );

        when(repository.getAll()).thenReturn(payments);

        try {
            List<Payment> result = useCase.execute();
            assertEquals(payments, result);
            verify(repository).getAll();
            System.out.println("Prueba exitosa: Se obtuvieron correctamente todos los pagos.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ReturnsListOfPayments: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_NoPayments_ReturnsEmptyList() {
        when(repository.getAll()).thenReturn(new ArrayList<>());

        try {
            List<Payment> result = useCase.execute();
            assertEquals(0, result.size());
            verify(repository).getAll();
            System.out.println("Prueba exitosa: Se validó correctamente que no hay pagos disponibles.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_NoPayments_ReturnsEmptyList: " + e.getMessage());
            throw e;
        }
    }

}
