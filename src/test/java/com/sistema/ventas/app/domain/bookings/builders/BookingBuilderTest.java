package com.sistema.ventas.app.domain.bookings.builders;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingBuilderTest {

    @Test
    @DisplayName("Debe construir un objeto Booking válido")
    void shouldBuildValidBooking() {
        // Crear un mock de Price
        Price price = mock(Price.class);
        when(price.getValue()).thenReturn(20.0);

        // Crear un mock de Route que devuelva el Price mockeado
        Route route = mock(Route.class);
        when(route.getPrice()).thenReturn(price);

        // Construir el objeto Booking usando BookingBuilder
        Booking booking = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(UUID.randomUUID())
                .withRouteId(route)
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(3)
                .withTotalPrice(60.0)
                .withReferenceNumber("ABC123")
                .withStatus(BookingStatus.RESERVADA)
                .build();

        // Verificaciones
        assertNotNull(booking);
        assertEquals(60.0, booking.getTotalPrice());
        assertEquals(BookingStatus.RESERVADA, booking.getStatus());
    }


    @Test
    @DisplayName("Debe fallar si el clientId es nulo")
    void shouldThrowExceptionWhenClientIdIsNull() {
        Route route = mock(Route.class);

        BookingBuilder builder = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(null) // clientId nulo
                .withRouteId(route)
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(3)
                .withTotalPrice(60.0)
                .withReferenceNumber("ABC123")
                .withStatus(BookingStatus.RESERVADA);

        assertThrows(RequiredFieldMissingException.class, builder::build);
    }

    @Test
    @DisplayName("Debe fallar si el routeId es nulo")
    void shouldThrowExceptionWhenRouteIdIsNull() {
        BookingBuilder builder = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(UUID.randomUUID())
                .withRouteId(null) // routeId nulo
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(3)
                .withTotalPrice(60.0)
                .withReferenceNumber("ABC123")
                .withStatus(BookingStatus.RESERVADA);

        assertThrows(RequiredFieldMissingException.class, builder::build);
    }

    @Test
    @DisplayName("Debe fallar si el número de personas es inválido")
    void shouldThrowExceptionWhenNumberOfPeopleIsInvalid() {
        Route route = mock(Route.class);

        BookingBuilder builder = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(UUID.randomUUID())
                .withRouteId(route)
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(0) // Número de personas inválido
                .withTotalPrice(60.0)
                .withReferenceNumber("ABC123")
                .withStatus(BookingStatus.RESERVADA);

        assertThrows(BusinessRuleViolationException.class, builder::build);
    }

    @Test
    @DisplayName("Debe fallar si el precio total es inválido")
    void shouldThrowExceptionWhenTotalPriceIsInvalid() {
        Route route = mock(Route.class);

        BookingBuilder builder = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(UUID.randomUUID())
                .withRouteId(route)
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(3)
                .withTotalPrice(0) // Precio inválido
                .withReferenceNumber("ABC123")
                .withStatus(BookingStatus.RESERVADA);

        assertThrows(BusinessRuleViolationException.class, builder::build);
    }

    @Test
    @DisplayName("Debe fallar si el estado de la reserva es nulo")
    void shouldThrowExceptionWhenStatusIsNull() {
        Route route = mock(Route.class);

        BookingBuilder builder = new BookingBuilder()
                .withId(UUID.randomUUID())
                .withClientId(UUID.randomUUID())
                .withRouteId(route)
                .withBookingDate(new Date())
                .withBookingTime(LocalTime.now())
                .withNumberOfPeople(3)
                .withTotalPrice(60.0)
                .withReferenceNumber("ABC123")
                .withStatus(null); // Estado nulo

        assertThrows(RequiredFieldMissingException.class, builder::build);
    }
}
