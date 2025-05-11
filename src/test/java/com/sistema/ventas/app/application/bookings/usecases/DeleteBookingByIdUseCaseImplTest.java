package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.UUID;

class DeleteBookingByIdUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;
    @Mock
    private BookingValidator bookingValidator;

    private DeleteBookingByIdUseCaseImpl deleteBookingByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteBookingByIdUseCase = new DeleteBookingByIdUseCaseImpl(bookingRepository, bookingValidator);
    }

    @Test
    void execute_ValidBooking_DeletesBooking() {
        // Arrange
        UUID bookingId = UUID.randomUUID();

        // Act
        deleteBookingByIdUseCase.execute(bookingId);

        // Assert
        verify(bookingValidator).validateAction("cancelar", bookingId);
        verify(bookingRepository).delete(bookingId);
    }
    @Test
    void execute_NonExistentBooking_ThrowsException() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        doThrow(new IllegalArgumentException("Booking not found")).when(bookingRepository).delete(bookingId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deleteBookingByIdUseCase.execute(bookingId));
    }

    @Test
    void execute_InvalidBookingId_ThrowsException() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        doThrow(new IllegalArgumentException("Invalid booking ID")).when(bookingValidator).validateAction("cancelar", bookingId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deleteBookingByIdUseCase.execute(bookingId));
    }
}
