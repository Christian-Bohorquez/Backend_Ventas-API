package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

class GetBookingByIdUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;
    @Mock
    private BookingValidator bookingValidator;

    private GetBookingByIdUseCaseImpl getBookingByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getBookingByIdUseCase = new GetBookingByIdUseCaseImpl(bookingRepository, bookingValidator);
    }

    @Test
    void execute_ValidBooking_ReturnsBooking() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        Booking booking = mock(Booking.class);
        when(bookingRepository.getById(bookingId)).thenReturn(Optional.of(booking));

        // Act
        Booking result = getBookingByIdUseCase.execute(bookingId);

        // Assert
        verify(bookingValidator).validateGetById(bookingId);
        assertEquals(booking, result);
    }

    @Test
    void execute_NonExistentBooking_ThrowsException() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        when(bookingRepository.getById(bookingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> getBookingByIdUseCase.execute(bookingId));
    }

    @Test
    void execute_InvalidBookingId_ThrowsException() {
        // Arrange
        UUID bookingId = UUID.randomUUID();
        doThrow(new IllegalArgumentException("Invalid ID")).when(bookingValidator).validateGetById(bookingId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> getBookingByIdUseCase.execute(bookingId));
    }
}