package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllBillingsUseCaseImplTest {

    private IBillingRepositoryPort repository;
    private GetAllBillingsUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = mock(IBillingRepositoryPort.class);
        useCase = new GetAllBillingsUseCaseImpl(repository);
    }

    @Test
    void execute_NoBillings_ReturnsEmptyList() {
        // GIVEN
        when(repository.getAll()).thenReturn(List.of());

        // WHEN
        List<Billing> result = useCase.execute();

        // THEN
        assertTrue(result.isEmpty());
        verify(repository).getAll();

        // Prueba exitosa: No se encontraron facturas y se devolvió una lista vacía.
        System.out.println("Prueba exitosa: No se encontraron facturas y la lista está vacía.");
    }

    @Test
    void execute_WithBillings_ReturnsBillingList() {
        // GIVEN
        Billing billing1 = mock(Billing.class);
        Billing billing2 = mock(Billing.class);
        when(repository.getAll()).thenReturn(List.of(billing1, billing2));

        // WHEN
        List<Billing> result = useCase.execute();

        // THEN
        assertEquals(2, result.size());
        assertTrue(result.contains(billing1));
        assertTrue(result.contains(billing2));
        verify(repository).getAll();

        // Prueba exitosa: Se devolvió la lista correcta con las facturas.
        System.out.println("Prueba exitosa: Se devolvió correctamente la lista de facturas.");
    }
}
