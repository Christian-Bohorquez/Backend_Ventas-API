package com.sistema.ventas.app.application.bookings.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.mappers.BookingMapperApp;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.ICreateBookingUseCase;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.in.IGetRouteByIdUseCase;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookingServiceTest {

    @Mock
    private BookingMapperApp mapper;
    @Mock private ICreateBookingUseCase createBookingUseCase;
    @Mock private IGetRouteByIdUseCase getRouteByIdUseCase;

    private BookingService bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService(mapper, createBookingUseCase, null, null, null, null, null, null, getRouteByIdUseCase);
    }

    @Test
    void createBooking_ValidBooking_ReturnsSuccess() {
        // Arrange
        BookingCreateDto dto = mock(BookingCreateDto.class);
        Route route = mock(Route.class);
        when(getRouteByIdUseCase.execute(dto.getRouteId())).thenReturn(route);
        Booking booking = mock(Booking.class);
        when(mapper.toBooking(dto, route)).thenReturn(booking);

        // Act
        ResponseAPI<String> response = bookingService.createBooking(dto);

        // Assert
        assertEquals(201, response.getStatusCode());
        assertEquals("Reserva creada exitosamente", response.getMessage());
    }

    @Test
    void createBooking_RouteNotFound_ReturnsError() {
        // Arrange
        BookingCreateDto dto = mock(BookingCreateDto.class);
        when(getRouteByIdUseCase.execute(dto.getRouteId())).thenThrow(new NotFoundException("Ruta"));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(dto));
    }
}
