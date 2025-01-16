package com.sistema.ventas.app.domain.bookings.ports.in;

import java.util.UUID;

public interface IDeleteBookingByIdUseCase {
    void execute(UUID id);
}
