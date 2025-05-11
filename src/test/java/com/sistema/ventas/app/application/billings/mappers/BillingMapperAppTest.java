package com.sistema.ventas.app.application.billings.mappers;

import com.sistema.ventas.app.application.billings.dtos.BillingCreateDto;
import com.sistema.ventas.app.application.billings.dtos.BillingGetDto;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.valueobjects.Email;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BillingMapperAppTest {

    private BillingMapperApp mapper;

    @BeforeEach
    void setup() {
        mapper = new BillingMapperApp();
    }

    @Test
    void toBilling_ValidBillingCreateDto_ReturnsBilling() throws NoSuchFieldException, IllegalAccessException {
        // GIVEN
        // Crear un mock de BillingCreateDto
        BillingCreateDto dto = mock(BillingCreateDto.class);
        when(dto.getCustomerIdentification()).thenReturn("0942673971");
        when(dto.getCustomerName()).thenReturn("Juan Pérez");
        when(dto.getCustomerEmail()).thenReturn("juan@gmail.com");
        when(dto.getPaymentId()).thenReturn(UUID.randomUUID());

        // Mock de Booking
        Booking booking = mock(Booking.class);
        UUID bookingId = UUID.randomUUID();

        // Definir la fecha de la reserva para que no sea vencida (por ejemplo, hoy)
        Date bookingDate = new Date(); // Fecha actual
        when(booking.getId()).thenReturn(bookingId);
        when(booking.getBookingDate()).thenReturn(bookingDate);
        when(booking.getStatus()).thenReturn(BookingStatus.RESERVADA); // El estado de la reserva es "RESERVADA"

        // Mock del precio total de la reserva
        double totalPrice = 100.0; // Establecer un precio mayor a cero
        when(booking.getTotalPrice()).thenReturn(totalPrice);

        // Mock de Email (sin usar el constructor privado)
        Email mockEmail = mock(Email.class);
        when(mockEmail.getValue()).thenReturn("juan@gmail.com");

        // Mock de Identification
        Identification mockIdentification = mock(Identification.class);
        when(mockIdentification.getValue()).thenReturn("0942673971");

        // Asegurarse de que la validación de la reserva no dispare la excepción
        // Mockeamos el método `getBookingDate` para que siempre devuelva una fecha futura, si es necesario
        when(booking.getBookingDate()).thenReturn(new Date(System.currentTimeMillis() + 1000000)); // Fecha futura

        // Llamada al método de mapeo
        Billing billing = mapper.toBilling(dto, booking);

        // THEN
        assertNotNull(billing, "El objeto Billing no debe ser null");
        assertEquals("Juan Pérez", billing.getCustomerName(), "El nombre del cliente no coincide");
        assertNotNull(billing.getId(), "El ID de la factura no debe ser null");
        assertEquals(bookingId, billing.getBooking().getId(), "La reserva no coincide");
        assertEquals("0942673971", billing.getIdentification().getValue(), "La identificación no coincide");
        assertTrue(billing.getTotalPrice() > 0, "El precio total debe ser mayor a cero");
        System.out.println("Prueba exitosa: El objeto Billing fue mapeado correctamente.");
    }


    @Test
    void toDtoGet_ValidBillingAndPayment_ReturnsBillingGetDto() {
        // GIVEN
        UUID billingId = UUID.randomUUID();
        UUID paymentId = UUID.randomUUID();
        Payment payment = mock(Payment.class);
        when(payment.getName()).thenReturn("TestPayment");

        // Mock de Billing
        Billing billing = mock(Billing.class);
        when(billing.getId()).thenReturn(billingId);
        when(billing.getCustomerName()).thenReturn("TestCustomer");

        // Mock de Email (sin usar el constructor privado)
        Email mockEmail = mock(Email.class);
        when(mockEmail.getValue()).thenReturn("test@gmail.com");
        when(billing.getCustomerEmail()).thenReturn(mockEmail);

        // Mock de Identification
        Identification mockIdentification = mock(Identification.class);
        when(mockIdentification.getValue()).thenReturn("12345");
        when(billing.getIdentification()).thenReturn(mockIdentification);

        when(billing.getTotalPrice()).thenReturn(200.0);
        when(billing.getBooking()).thenReturn(mock(Booking.class));

        // WHEN
        BillingGetDto dto = mapper.toDtoGet(billing, payment);

        // THEN
        assertNotNull(dto, "El objeto BillingGetDto no debe ser null");
        assertEquals(billingId, dto.getId(), "El ID de la factura no coincide");
        assertEquals("TestCustomer", dto.getCustomerName(), "El nombre del cliente no coincide");
        assertEquals("test@gmail.com", dto.getCustomerEmail(), "El correo electrónico no coincide");
        assertEquals("TestPayment", dto.getPaymentName(), "El nombre del pago no coincide");
        assertEquals(200.0, dto.getTotalPrice(), "El precio total no coincide");
        System.out.println("Prueba exitosa: El objeto BillingGetDto fue mapeado correctamente.");
    }



    @Test
    void toDtoGet_NullBilling_ThrowsException() {
        // GIVEN
        Billing billing = null;
        Payment payment = new Payment(UUID.randomUUID(), "TestPayment");

        // WHEN & THEN
        assertThrows(NullPointerException.class, () -> mapper.toDtoGet(billing, payment), "Debería lanzar una excepción si el Billing es null");
        System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un Billing null.");
    }

    @Test
    void toDtoGet_NullPayment_ThrowsException() {
        // GIVEN
        Billing billing = Billing.create(UUID.randomUUID(), "0942673971", "TestCustomer", "test@gmail.com", mock(Booking.class), new Date(), UUID.randomUUID(), 200.0);
        Payment payment = null;

        // WHEN & THEN
        assertThrows(NullPointerException.class, () -> mapper.toDtoGet(billing, payment), "Debería lanzar una excepción si el Payment es null");
        System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un Payment null.");
    }

    @Test
    void toBilling_NullBillingCreateDto_ThrowsException() {
        // GIVEN
        BillingCreateDto dto = null;
        Booking booking = mock(Booking.class);

        // WHEN & THEN
        assertThrows(NullPointerException.class, () -> mapper.toBilling(dto, booking), "Debería lanzar una excepción si el BillingCreateDto es null");
        System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un BillingCreateDto null.");
    }
}
