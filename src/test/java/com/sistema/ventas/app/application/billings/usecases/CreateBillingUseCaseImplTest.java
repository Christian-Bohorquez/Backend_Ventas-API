package com.sistema.ventas.app.application.billings.usecases;

import static org.mockito.Mockito.*;

import com.sistema.ventas.app.application.billings.validators.BillingValidator;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.valueobjects.Email;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateBillingUseCaseImplTest {

    private IBillingRepositoryPort billingRepository;
    private BillingValidator validator;
    private CreateBillingUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        billingRepository = Mockito.mock(IBillingRepositoryPort.class);
        validator = Mockito.mock(BillingValidator.class);
        useCase = new CreateBillingUseCaseImpl(billingRepository, validator);
    }

    @Test
    void execute_ValidBilling_CreatesBilling() {
        // Crear una instancia válida de Identification y Email usando mocks
        Identification identification = Mockito.mock(Identification.class);
        Email customerEmail = Mockito.mock(Email.class);

        // Crear una instancia mock de Booking
        Booking booking = Mockito.mock(Booking.class);

        // Crear una instancia válida de Billing
        UUID billingId = UUID.randomUUID();
        String customerName = "John Doe";
        Date billingDate = new Date();
        UUID paymentId = UUID.randomUUID();
        double totalPrice = 100.50;

        // Mock del constructor o método de fábrica de Billing si es necesario
        Billing billing = Mockito.mock(Billing.class);

        try {
            // Ejecutar el caso de uso
            useCase.execute(billing);

            // Validaciones del comportamiento esperado
            verify(validator).validateCreate(billing);
            verify(billingRepository).save(billing);

            System.out.println("Prueba exitosa: Se creó correctamente la factura.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidBilling_CreatesBilling: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidBilling_ThrowsException() {
        // Crear una instancia válida de Identification y Email usando mocks
        Identification identification = Mockito.mock(Identification.class);
        Email customerEmail = Mockito.mock(Email.class);

        // Crear una instancia mock de Booking
        Booking booking = Mockito.mock(Booking.class);

        // Crear una instancia válida de Billing
        UUID billingId = UUID.randomUUID();
        String customerName = "John Doe";
        Date billingDate = new Date();
        UUID paymentId = UUID.randomUUID();
        double totalPrice = 100.50;

        // Mock del constructor o método de fábrica de Billing si es necesario
        Billing billing = Mockito.mock(Billing.class);

        // Simular que la validación lanza una excepción
        doThrow(new NotFoundException("Reserva")).when(validator).validateCreate(billing);

        try {
            // Ejecutar y verificar que lanza la excepción
            assertThrows(NotFoundException.class, () -> useCase.execute(billing));
            verify(validator).validateCreate(billing);
            verify(billingRepository, never()).save(billing);

            System.out.println("Prueba exitosa: Se lanzó la excepción NotFoundException.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidBilling_ThrowsException: " + e.getMessage());
            throw e;
        }
    }

}