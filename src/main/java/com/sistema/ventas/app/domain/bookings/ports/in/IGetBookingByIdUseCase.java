package com.sistema.ventas.app.domain.bookings.ports.in;

import com.sistema.ventas.app.domain.bookings.models.Booking;

import java.util.UUID;

public interface IGetBookingByIdUseCase {
    Booking execute(UUID id);
}
