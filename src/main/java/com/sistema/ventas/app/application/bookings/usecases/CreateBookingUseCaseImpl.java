package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.ICreateBookingUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CreateBookingUseCaseImpl implements ICreateBookingUseCase {

    private final IBookingRepositoryPort _repository;
    private final BookingValidator _validator;

    public CreateBookingUseCaseImpl(
            IBookingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Booking booking) {
        _validator.validateCreate(booking);
        _repository.save(booking);
    }

}
