package com.sistema.ventas.app.application.billings.usecases;

import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

class DeleteBillingByIdUseCaseImplTest {

    private IBillingRepositoryPort repository;
    private BookingValidator validator;
    private DeleteBillingByIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = mock(IBillingRepositoryPort.class);
        validator = mock(BookingValidator.class);
        useCase = new DeleteBillingByIdUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidId_DeletesBilling() {
        UUID validId = UUID.randomUUID();

        // GIVEN
        doNothing().when(validator).validateGetById(validId);
        doNothing().when(repository).delete(validId);

        // WHEN
        useCase.execute(validId);

        // THEN
        verify(validator).validateGetById(validId);
        verify(repository).delete(validId);
        // Prueba exitosa: Se ha validado correctamente el ID y se ha eliminado la factura.
        System.out.println("Prueba exitosa: El ID válido fue procesado y la factura fue eliminada.");

    }

    @Test
    void execute_InvalidId_ThrowsNotFoundException() {
        UUID invalidId = UUID.randomUUID();

        // GIVEN
        doThrow(new NotFoundException("Factura")).when(validator).validateGetById(invalidId);

        // WHEN & THEN
        assertThrows(NotFoundException.class, () -> useCase.execute(invalidId));
        verify(validator).validateGetById(invalidId);
        verify(repository, never()).delete(invalidId);
        // Prueba exitosa: La excepción NotFoundException fue lanzada correctamente cuando el ID no es válido.
        System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para el ID no válido.");

    }
}