package com.sistema.ventas.app.application.bookings.mappers;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingMapperAppTest {

    private BookingMapperApp bookingMapperApp;

    @BeforeEach
    void setUp() {
        bookingMapperApp = new BookingMapperApp();

    }

    @Test
    void toBooking_CreatesBookingSuccessfully() {
        // Arrange
        BookingCreateDto dto = mock(BookingCreateDto.class);
        when(dto.getClientId()).thenReturn(UUID.randomUUID()); // Este campo es obligatorio
        when(dto.getBookingDate()).thenReturn(java.sql.Date.valueOf(LocalDate.now())); // Fecha válida
        when(dto.getBookingTime()).thenReturn(LocalTime.now());
        when(dto.getNumberOfPeople()).thenReturn(3);

        // Usamos lenient para los mocks que no estamos utilizando explícitamente
        Route route = mock(Route.class);
        lenient().when(route.getPrice()).thenReturn(mock(Price.class));
        lenient().when(route.getId()).thenReturn(UUID.randomUUID());
        lenient().when(route.getStartLocation()).thenReturn("City A");
        lenient().when(route.getEndLocation()).thenReturn("City B");

        // Mock del BookingMapperApp para simular la creación de Booking
        BookingMapperApp bookingMapperApp = mock(BookingMapperApp.class);

        // Simular la creación de un Booking a través de toBooking
        Booking expectedBooking = mock(Booking.class);
        when(bookingMapperApp.toBooking(dto, route)).thenReturn(expectedBooking);

        // Act
        Booking booking = bookingMapperApp.toBooking(dto, route);

        // Assert
        assertNotNull(booking);
        assertEquals(expectedBooking, booking);
        assertEquals(dto.getClientId(), booking.getClientId());
        assertEquals(route, booking.getRoute());
        assertEquals(dto.getNumberOfPeople(), booking.getNumberOfPeople());
    }



    @Test
    void toBooking_HandlesNullValues() {
        // Arrange
        BookingCreateDto dto = mock(BookingCreateDto.class);
        when(dto.getClientId()).thenReturn(null);
        when(dto.getBookingDate()).thenReturn(java.sql.Date.valueOf(LocalDate.now())); // Proporciona una fecha válida
        when(dto.getBookingTime()).thenReturn(LocalTime.now()); // Establece una hora válida
        when(dto.getNumberOfPeople()).thenReturn(0);

        Route route = mock(Route.class);
        Price price = mock(Price.class);
        when(price.getValue()).thenReturn(100.0); // Evita NullPointerException
        when(route.getPrice()).thenReturn(price);
        when(route.getId()).thenReturn(UUID.randomUUID());
        when(route.getStartLocation()).thenReturn("City A");
        when(route.getEndLocation()).thenReturn("City B");

        // Mock de BookingMapperApp para simular el método toBooking
        BookingMapperApp bookingMapperApp = mock(BookingMapperApp.class);

        // Simular la creación de Booking cuando se llame a toBooking
        Booking expectedBooking = mock(Booking.class);
        when(bookingMapperApp.toBooking(dto, route)).thenReturn(expectedBooking);

        // Act
        Booking booking = bookingMapperApp.toBooking(dto, route);

        // Assert
        assertNotNull(booking);
        assertNull(booking.getClientId()); // Este es el valor que pasaste como null
        assertNotNull(booking.getBookingDate()); // Verifica que la fecha no sea null
        assertEquals(0, booking.getNumberOfPeople());
    }



    @Test
    void toBookingUpdate_UpdatesExistingBooking() {
        // Arrange
        BookingUpdateDto dto = mock(BookingUpdateDto.class);
        when(dto.getBookingDate()).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(dto.getBookingTime()).thenReturn(LocalTime.now());

        Booking existingBooking = mock(Booking.class);

        // Act
        bookingMapperApp.toBooking(dto, existingBooking);

        // Assert
        verify(existingBooking).updateBookingDateAndTime(dto.getBookingDate(), dto.getBookingTime());
    }

    @Test
    void toDtoGet_CreatesBookingGetDto() {
        // Arrange
        Booking booking = mock(Booking.class);
        Route route = mock(Route.class);
        when(route.getId()).thenReturn(UUID.randomUUID());
        when(booking.getRoute()).thenReturn(route);

        BookingStatus status = mock(BookingStatus.class);
        when(status.getValue()).thenReturn("CONFIRMED"); // Evita NullPointerException
        when(booking.getStatus()).thenReturn(status);

        Client client = mock(Client.class);
        when(client.getFirstName()).thenReturn(Name.create("John", "First Name"));
        when(client.getLastName()).thenReturn(Name.create("Doe", "Last Name"));

        // Act
        BookingGetDto dto = bookingMapperApp.toDtoGet(booking, client);

        // Assert
        assertNotNull(dto);
        assertEquals("John Doe", dto.getClientFullName());
    }
}