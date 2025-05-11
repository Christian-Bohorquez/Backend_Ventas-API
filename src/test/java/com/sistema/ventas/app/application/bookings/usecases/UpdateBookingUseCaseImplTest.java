package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.UUID;

class UpdateBookingUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;
    @Mock
    private BookingValidator bookingValidator;

    private UpdateBookingUseCaseImpl updateBookingUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateBookingUseCase = new UpdateBookingUseCaseImpl(bookingRepository, bookingValidator);
    }

    @Test
    void execute_ValidBooking_UpdatesBooking() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(bookingId);

        // Act
        updateBookingUseCase.execute(booking);

        // Assert
        verify(bookingValidator).validateAction("actualizar", bookingId);
        verify(bookingRepository).save(booking);
    }
    @Test
    void execute_BookingWithNullId_ThrowsException() {
        // Arrange
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(null);

        // Configurar el validador para lanzar una excepción cuando el ID es nulo
        doThrow(new IllegalArgumentException("Booking ID cannot be null"))
                .when(bookingValidator).validateAction("actualizar", null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateBookingUseCase.execute(booking));
    }
    @Test
    void execute_InvalidBooking_ThrowsException() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(bookingId);

        // Configurar el validador para lanzar una excepción cuando el booking es inválido
        doThrow(new IllegalArgumentException("Invalid booking"))
                .when(bookingValidator).validateAction("actualizar", bookingId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateBookingUseCase.execute(booking));
    }
}
