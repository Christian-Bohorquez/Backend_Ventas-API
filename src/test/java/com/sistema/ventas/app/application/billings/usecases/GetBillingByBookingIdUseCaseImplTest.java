package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetBillingByBookingIdUseCaseImplTest {

    private IBillingRepositoryPort repository;
    private BookingValidator validator;
    private GetBillingByBookingIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = mock(IBillingRepositoryPort.class);
        validator = mock(BookingValidator.class);
        useCase = new GetBillingByBookingIdUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidBookingId_ReturnsBilling() {
        UUID bookingId = UUID.randomUUID();
        Billing billing = mock(Billing.class);

        // GIVEN
        doNothing().when(validator).validateGetById(bookingId);
        when(repository.getByBookingId(bookingId)).thenReturn(Optional.of(billing));

        // WHEN
        Billing result = useCase.execute(bookingId);

        // THEN
        assertNotNull(result);
        assertEquals(billing, result);
        verify(validator).validateGetById(bookingId);
        verify(repository).getByBookingId(bookingId);

        // Prueba exitosa: Se devolvió la factura correctamente para el ID de reserva válido.
        System.out.println("Prueba exitosa: Se devolvió la factura para el ID de reserva válido.");
    }

    @Test
    void execute_InvalidBookingId_ThrowsNoSuchElementException() {
        UUID bookingId = UUID.randomUUID();

        // GIVEN
        doNothing().when(validator).validateGetById(bookingId);
        when(repository.getByBookingId(bookingId)).thenReturn(Optional.empty());

        // WHEN & THEN
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> useCase.execute(bookingId));

        // Verifica que el mensaje sea correcto (en este caso, será el mensaje predeterminado de NoSuchElementException)
        assertEquals("No value present", exception.getMessage());

        // Verifica las interacciones
        verify(validator).validateGetById(bookingId);
        verify(repository).getByBookingId(bookingId);

        // Prueba exitosa: La excepción NoSuchElementException se lanzó correctamente para el ID de reserva inválido.
        System.out.println("Prueba exitosa: Se lanzó la excepción NoSuchElementException para el ID de reserva inválido.");
    }
}
