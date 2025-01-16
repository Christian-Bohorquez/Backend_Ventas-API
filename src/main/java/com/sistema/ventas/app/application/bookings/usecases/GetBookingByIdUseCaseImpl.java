package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.IGetBookingByIdUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetBookingByIdUseCaseImpl implements IGetBookingByIdUseCase {

    private final IBookingRepositoryPort _repository;
    private final BookingValidator _validator;

    public GetBookingByIdUseCaseImpl(
            IBookingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Booking execute(UUID id) {
        _validator.validateGetById(id);
        return _repository.getById(id).get();
    }

}
