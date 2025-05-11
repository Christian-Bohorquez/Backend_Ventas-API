package com.sistema.ventas.app.domain.billings.builders;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillingBuilderTest {

    @Test
    @DisplayName("✅ Debe construir un Billing correctamente con el Builder")
    void shouldBuildBillingWithBuilder() {
        // Crear un Booking mock válido
        Booking booking = mock(Booking.class);
        when(booking.getStatus()).thenReturn(BookingStatus.RESERVADA);
        when(booking.getTotalPrice()).thenReturn(100.0);
        when(booking.getBookingDate()).thenReturn(new Date());

        // Usar el BillingBuilder
        Billing billing = new BillingBuilder()
                .withId(UUID.randomUUID())
                .withIdentification("0951618024")
                .withCustomerName("Juan Perez")
                .withCustomerEmail("juan.perez@gmail.com")
                .withBooking(booking)
                .withBillingDate(new Date())
                .withPaymentId(UUID.randomUUID())
                .withTotalPrice(112.0)
                .build();

        assertNotNull(billing);
        assertEquals("0951618024", billing.getIdentification().getValue());
        assertEquals("Juan Perez", billing.getCustomerName());
        assertEquals("juan.perez@gmail.com", billing.getCustomerEmail().getValue());
        assertEquals(112.0, billing.getTotalPrice());
        assertEquals(booking, billing.getBooking());
    }
}
