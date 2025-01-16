package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.IGetAllBookingsUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBookingsUseCaseImpl implements IGetAllBookingsUseCase {

    private final IBookingRepositoryPort _repository;

    public GetAllBookingsUseCaseImpl(IBookingRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public List<Booking> execute() {
        return _repository.getAll();
    }

}