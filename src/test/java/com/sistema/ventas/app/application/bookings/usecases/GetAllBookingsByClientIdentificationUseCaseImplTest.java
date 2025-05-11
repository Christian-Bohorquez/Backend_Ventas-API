package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class GetAllBookingsByClientIdentificationUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;

    @Mock
    private ClientValidator clientValidator;

    @InjectMocks
    private GetAllBookingsByClientIdentificationUseCaseImpl getAllBookingsByClientIdentificationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ValidIdentification_ReturnsBookings() {
        // Arrange
        String identification = "0942673971";
        List<Booking> expectedBookings = Arrays.asList(mock(Booking.class), mock(Booking.class));

        // Configurar el comportamiento del repositorio
        when(bookingRepository.getByClientIdentification(identification)).thenReturn(expectedBookings);

        // Act
        List<Booking> result = getAllBookingsByClientIdentificationUseCase.execute(identification);

        // Assert
        // Verificar que se llamó al validador con la identificación correcta
        verify(clientValidator).validateGetByIdentification(identification);

        // Verificar que se llamó al repositorio con la identificación correcta
        verify(bookingRepository).getByClientIdentification(identification);

        // Verificar que se devolvió la lista de reservas esperada
        assertEquals(expectedBookings, result);
    }

    @Test
    void execute_InvalidIdentification_ThrowsException() {
        // Arrange
        String invalidIdentification = "invalid-id";

        // Configurar el validador para lanzar una excepción cuando la identificación es inválida
        doThrow(new IllegalArgumentException("Invalid identification"))
                .when(clientValidator).validateGetByIdentification(invalidIdentification);

        // Act & Assert
        // Verificar que se lanza una excepción
        assertThrows(IllegalArgumentException.class, () -> {
            getAllBookingsByClientIdentificationUseCase.execute(invalidIdentification);
        });

        // Verificar que no se llamó al repositorio
        verify(bookingRepository, never()).getByClientIdentification(anyString());
    }
}