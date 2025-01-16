package com.sistema.ventas.app.domain.bookings.ports.out;

import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.shared.repositories.IRepository;

import java.util.List;

public interface IBookingRepositoryPort extends IRepository<Booking> {

    List<Booking> getByClientIdentification(String identification);

}
