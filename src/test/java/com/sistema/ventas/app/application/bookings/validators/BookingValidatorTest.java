package com.sistema.ventas.app.application.bookings.validators;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.mockito.*;
import org.junit.jupiter.api.*;
import java.util.*;

class BookingValidatorTest {

    @Mock private IBookingRepositoryPort bookingRepository;
    @Mock private IRouteRepositoryPort routeRepository;
    @Mock private IClientRepositoryPort clientRepository;

    private BookingValidator bookingValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        bookingValidator = new BookingValidator(bookingRepository, routeRepository, clientRepository);
    }
    @Test
    void validateCreate_ValidBooking_DoesNotThrowException() {
        // Arrange
        Booking booking = mock(Booking.class);
        Route route = mock(Route.class);
        Client client = mock(Client.class);

        // Asegúrate de que la fecha esté en el futuro
        when(booking.getBookingDate()).thenReturn(new Date(System.currentTimeMillis() + 10000)); // fecha futura
        when(booking.getClientId()).thenReturn(UUID.randomUUID());
        when(booking.getRoute()).thenReturn(route);  // Asegúrate de mockear el método getRoute
        when(booking.getRoute().getId()).thenReturn(UUID.randomUUID()); // Mockea el método getId
        when(clientRepository.getById(booking.getClientId())).thenReturn(Optional.of(client));
        when(routeRepository.getById(booking.getRoute().getId())).thenReturn(Optional.of(route));

        // Act & Assert
        assertDoesNotThrow(() -> bookingValidator.validateCreate(booking));
    }

    @Test
    void validateCreate_InvalidBooking_ThrowsBusinessRuleViolationException() {
        // Arrange
        Booking booking = mock(Booking.class);
        Route route = mock(Route.class);
        Client client = mock(Client.class);

        when(booking.getBookingDate()).thenReturn(new Date(System.currentTimeMillis() - 10000)); // fecha pasada
        when(booking.getClientId()).thenReturn(UUID.randomUUID());
        when(booking.getRoute()).thenReturn(route); // Asegúrate de mockear el método getRoute
        when(booking.getRoute().getId()).thenReturn(UUID.randomUUID()); // Mockea el método getId
        when(clientRepository.getById(booking.getClientId())).thenReturn(Optional.of(client));
        when(routeRepository.getById(booking.getRoute().getId())).thenReturn(Optional.of(route));

        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> bookingValidator.validateCreate(booking));
    }

    @Test
    void validateCreate_ClientNotFound_ThrowsNotFoundException() {
        // Arrange
        Booking booking = mock(Booking.class);
        Route route = mock(Route.class);

        // Asegúrate de que la fecha esté en el futuro para evitar el BusinessRuleViolationException
        when(booking.getBookingDate()).thenReturn(new Date(System.currentTimeMillis() + 10000)); // fecha futura
        when(booking.getClientId()).thenReturn(UUID.randomUUID());
        when(booking.getRoute()).thenReturn(route);
        when(booking.getRoute().getId()).thenReturn(UUID.randomUUID());
        when(clientRepository.getById(booking.getClientId())).thenReturn(Optional.empty()); // Cliente no encontrado
        when(routeRepository.getById(booking.getRoute().getId())).thenReturn(Optional.of(route));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookingValidator.validateCreate(booking));
    }
    @Test
    void validateCreate_RouteNotFound_ThrowsNotFoundException() {
        // Arrange
        Booking booking = mock(Booking.class);
        Client client = mock(Client.class);

        // Asegúrate de que la fecha esté en el futuro para evitar el BusinessRuleViolationException
        when(booking.getBookingDate()).thenReturn(new Date(System.currentTimeMillis() + 10000)); // fecha futura
        when(booking.getClientId()).thenReturn(UUID.randomUUID());
        when(booking.getRoute()).thenReturn(mock(Route.class)); // Asegúrate de mockear el método getRoute
        when(booking.getRoute().getId()).thenReturn(UUID.randomUUID()); // Mockea el método getId
        when(clientRepository.getById(booking.getClientId())).thenReturn(Optional.of(client));
        when(routeRepository.getById(booking.getRoute().getId())).thenReturn(Optional.empty()); // Ruta no encontrada

        // Act & Assert
        assertThrows(NotFoundException.class, () -> bookingValidator.validateCreate(booking));
    }

}