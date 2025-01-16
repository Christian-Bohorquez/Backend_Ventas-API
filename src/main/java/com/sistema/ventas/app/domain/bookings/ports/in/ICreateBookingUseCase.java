package com.sistema.ventas.app.domain.bookings.ports.in;

import com.sistema.ventas.app.domain.bookings.models.Booking;

public interface ICreateBookingUseCase {
    void execute(Booking booking);
}
