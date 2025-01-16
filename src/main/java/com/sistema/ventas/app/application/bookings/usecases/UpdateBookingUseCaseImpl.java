package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.IUpdateBookingUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateBookingUseCaseImpl implements IUpdateBookingUseCase {

    private final IBookingRepositoryPort _repository;
    private final BookingValidator _validator;

    public UpdateBookingUseCaseImpl(
            IBookingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Booking booking) {
        _validator.validateAction("actualizar",booking.getId());
        _repository.save(booking);
    }

}
