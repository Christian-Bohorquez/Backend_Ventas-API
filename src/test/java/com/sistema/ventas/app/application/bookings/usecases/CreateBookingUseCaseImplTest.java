package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

class CreateBookingUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;
    @Mock
    private BookingValidator bookingValidator;

    private CreateBookingUseCaseImpl createBookingUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createBookingUseCase = new CreateBookingUseCaseImpl(bookingRepository, bookingValidator);
    }

    @Test
    void execute_ValidBooking_CreatesBooking() {
        // Arrange
        Booking booking = mock(Booking.class);

        // Act
        createBookingUseCase.execute(booking);

        // Assert
        verify(bookingValidator).validateCreate(booking);
        verify(bookingRepository).save(booking);
    }
    @Test
    void execute_InvalidBooking_ThrowsException() {
        // Arrange
        Booking booking = mock(Booking.class);
        doThrow(new IllegalArgumentException("Invalid booking")).when(bookingRepository).save(booking);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createBookingUseCase.execute(booking));
    }

    @Test
    void execute_BookingWithNullValues_ThrowsException() {
        // Arrange
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(null);

        // Configurar el validador para lanzar una excepción cuando se valide un booking con valores nulos
        doThrow(new IllegalArgumentException("Booking ID cannot be null")).when(bookingValidator).validateCreate(booking);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> createBookingUseCase.execute(booking));
    }
}
