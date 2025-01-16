package com.sistema.ventas.app.domain.bookings.ports.in;

import com.sistema.ventas.app.domain.bookings.models.Booking;

import java.util.List;

public interface IGetAllBookingsByClientIdentificationUseCase {
    List<Booking> execute(String identification);
}
