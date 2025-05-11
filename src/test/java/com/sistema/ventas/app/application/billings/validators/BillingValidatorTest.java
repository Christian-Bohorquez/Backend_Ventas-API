package com.sistema.ventas.app.application.billings.validators;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillingValidatorTest {

    private IBillingRepositoryPort billingRepository;
    private IBookingRepositoryPort bookingRepository;
    private IPaymentRepositoryPort paymentRepository;
    private BillingValidator validator;

    @BeforeEach
    void setup() {
        billingRepository = Mockito.mock(IBillingRepositoryPort.class);
        bookingRepository = Mockito.mock(IBookingRepositoryPort.class);
        paymentRepository = Mockito.mock(IPaymentRepositoryPort.class);
        validator = new BillingValidator(billingRepository, bookingRepository, paymentRepository);
    }

    @Test
    void validateCreate_ValidBilling_DoesNotThrowException() {
        // GIVEN
        Billing billing = Mockito.mock(Billing.class);
        Booking booking = Mockito.mock(Booking.class);
        Payment payment = Mockito.mock(Payment.class);

        // Configuración de los mocks
        when(billing.getBooking()).thenReturn(booking);
        when(booking.getId()).thenReturn(UUID.randomUUID());
        when(billing.getPaymentId()).thenReturn(UUID.randomUUID());

        // Simulamos que la reserva y el pago existen
        when(bookingRepository.getById(billing.getBooking().getId())).thenReturn(Optional.of(booking));
        when(paymentRepository.getById(billing.getPaymentId())).thenReturn(Optional.of(payment));

        try {
            // WHEN & THEN
            assertDoesNotThrow(() -> validator.validateCreate(billing));
            verify(bookingRepository).getById(billing.getBooking().getId());
            verify(paymentRepository).getById(billing.getPaymentId());
            System.out.println("Prueba exitosa: Se validó correctamente la creación de la factura.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_ValidBilling_DoesNotThrowException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateCreate_InvalidBooking_ThrowsNotFoundException() {
        // GIVEN
        Billing billing = Mockito.mock(Billing.class);
        Booking booking = Mockito.mock(Booking.class);

        // Configuración de los mocks
        when(billing.getBooking()).thenReturn(booking);
        when(booking.getId()).thenReturn(UUID.randomUUID());

        // Simulamos que la reserva no existe
        when(bookingRepository.getById(billing.getBooking().getId())).thenReturn(Optional.empty());

        try {
            // WHEN & THEN
            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> validator.validateCreate(billing)
            );
            // Ajustamos para que coincida con el mensaje completo o parte relevante
            assertTrue(exception.getMessage().contains("Reserva"));
            verify(bookingRepository).getById(billing.getBooking().getId());
            verify(paymentRepository, never()).getById(billing.getPaymentId());
            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para la reserva inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_InvalidBooking_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateCreate_InvalidPayment_ThrowsNotFoundException() {
        // GIVEN
        Billing billing = Mockito.mock(Billing.class);
        Booking booking = Mockito.mock(Booking.class);

        // Configuración de los mocks
        when(billing.getBooking()).thenReturn(booking);
        when(booking.getId()).thenReturn(UUID.randomUUID());
        when(billing.getPaymentId()).thenReturn(UUID.randomUUID());

        // Simulamos que el pago no existe
        when(bookingRepository.getById(billing.getBooking().getId())).thenReturn(Optional.of(booking));
        when(paymentRepository.getById(billing.getPaymentId())).thenReturn(Optional.empty());

        try {
            // WHEN & THEN
            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> validator.validateCreate(billing)
            );
            // Ajustamos para que coincida con el mensaje completo o parte relevante
            assertTrue(exception.getMessage().contains("Forma de pago"));
            verify(bookingRepository).getById(billing.getBooking().getId());
            verify(paymentRepository).getById(billing.getPaymentId());
            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para el pago inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_InvalidPayment_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateGetById_ValidId_DoesNotThrowException() {
        UUID billingId = UUID.randomUUID();

        // Simulamos que la factura existe
        Billing billing = Mockito.mock(Billing.class);
        when(billingRepository.getById(billingId)).thenReturn(Optional.of(billing));

        try {
            // WHEN & THEN
            assertDoesNotThrow(() -> validator.validateGetById(billingId));
            verify(billingRepository).getById(billingId);
            System.out.println("Prueba exitosa: Se validó correctamente la existencia de la factura.");
        } catch (AssertionError e) {
            System.out.println("Error en validateGetById_ValidId_DoesNotThrowException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateGetById_InvalidId_ThrowsNotFoundException() {
        UUID billingId = UUID.randomUUID();

        // Simulamos que la factura no existe
        when(billingRepository.getById(billingId)).thenReturn(Optional.empty());

        try {
            // WHEN & THEN
            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> validator.validateGetById(billingId)
            );
            // Ajustamos para que coincida con el mensaje completo o parte relevante
            assertTrue(exception.getMessage().contains("Factura"));
            verify(billingRepository).getById(billingId);
            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException para la factura inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en validateGetById_InvalidId_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }
}