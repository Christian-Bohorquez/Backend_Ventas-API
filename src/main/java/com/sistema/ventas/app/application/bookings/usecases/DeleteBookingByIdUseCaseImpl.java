package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.bookings.ports.in.IDeleteBookingByIdUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteBookingByIdUseCaseImpl implements IDeleteBookingByIdUseCase {

    private final IBookingRepositoryPort _repository;
    private final BookingValidator _validator;

    public DeleteBookingByIdUseCaseImpl(
            IBookingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(UUID id) {
        _validator.validateAction("cancelar",id);
        _repository.delete(id);
    }
}
