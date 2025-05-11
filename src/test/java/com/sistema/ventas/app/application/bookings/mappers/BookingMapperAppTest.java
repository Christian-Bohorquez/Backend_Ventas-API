package com.sistema.ventas.app.application.bookings.mappers;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
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
    void toBooking_CreatesBookingSuccessfully() throws Exception {
        // Arrange
        BookingCreateDto dto = new BookingCreateDto();

        Field clientIdField = BookingCreateDto.class.getDeclaredField("clientId");
        clientIdField.setAccessible(true);
        UUID clientId = UUID.randomUUID();
        clientIdField.set(dto, clientId);

        Field dateField = BookingCreateDto.class.getDeclaredField("bookingDate");
        dateField.setAccessible(true);
        dateField.set(dto, java.sql.Date.valueOf(LocalDate.now()));

        Field timeField = BookingCreateDto.class.getDeclaredField("bookingTime");
        timeField.setAccessible(true);
        timeField.set(dto, LocalTime.of(10, 0));

        Field peopleField = BookingCreateDto.class.getDeclaredField("numberOfPeople");
        peopleField.setAccessible(true);
        peopleField.set(dto, 3);

        Price price = Price.create(10.0);
        Route route = mock(Route.class);
        when(route.getPrice()).thenReturn(price);


        // Act
        Booking booking = bookingMapperApp.toBooking(dto, route);

        // Assert
        assertNotNull(booking);
        assertEquals(clientId, booking.getClientId());
        assertEquals(3, booking.getNumberOfPeople());
        assertEquals(30.0, booking.getTotalPrice());
        assertEquals(route, booking.getRoute());
    }




    @Test
    void toBooking_InvalidPeopleCount_ThrowsBusinessRuleViolationException() throws Exception {
        // Arrange
        BookingCreateDto dto = new BookingCreateDto();

        Field clientIdField = BookingCreateDto.class.getDeclaredField("clientId");
        clientIdField.setAccessible(true);
        clientIdField.set(dto, UUID.randomUUID()); // No null, para que pase esa validación

        Field dateField = BookingCreateDto.class.getDeclaredField("bookingDate");
        dateField.setAccessible(true);
        dateField.set(dto, java.sql.Date.valueOf(LocalDate.now()));

        Field timeField = BookingCreateDto.class.getDeclaredField("bookingTime");
        timeField.setAccessible(true);
        timeField.set(dto, LocalTime.now());

        Field peopleField = BookingCreateDto.class.getDeclaredField("numberOfPeople");
        peopleField.setAccessible(true);
        peopleField.set(dto, 0); // causa error de lógica de negocio

        Price price = Price.create(100.0);
        Route route = mock(Route.class);
        when(route.getPrice()).thenReturn(price);

        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> {
            bookingMapperApp.toBooking(dto, route);
        }, "Debe lanzar excepción porque el número de personas es 0");
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