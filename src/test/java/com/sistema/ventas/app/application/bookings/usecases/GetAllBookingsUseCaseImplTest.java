package com.sistema.ventas.app.application.bookings.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;

class GetAllBookingsUseCaseImplTest {

    @Mock
    private IBookingRepositoryPort bookingRepository;

    private GetAllBookingsUseCaseImpl getAllBookingsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllBookingsUseCase = new GetAllBookingsUseCaseImpl(bookingRepository);
    }

    @Test
    void execute_ReturnsBookings() {
        // Arrange
        List<Booking> bookings = List.of(mock(Booking.class), mock(Booking.class));
        when(bookingRepository.getAll()).thenReturn(bookings);

        // Act
        List<Booking> result = getAllBookingsUseCase.execute();

        // Assert
        assertEquals(bookings, result);
    }
    @Test
    void execute_NoBookings_ReturnsEmptyList() {
        // Arrange
        when(bookingRepository.getAll()).thenReturn(List.of());

        // Act
        List<Booking> result = getAllBookingsUseCase.execute();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void execute_ExceptionDuringFetch_ThrowsException() {
        // Arrange
        when(bookingRepository.getAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> getAllBookingsUseCase.execute());
    }
}
