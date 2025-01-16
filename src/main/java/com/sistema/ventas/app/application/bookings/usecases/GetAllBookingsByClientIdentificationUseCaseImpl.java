package com.sistema.ventas.app.application.bookings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.in.IGetAllBookingsByClientIdentificationUseCase;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBookingsByClientIdentificationUseCaseImpl implements IGetAllBookingsByClientIdentificationUseCase {

    private final IBookingRepositoryPort _repository;
    private final ClientValidator _validator;

    public GetAllBookingsByClientIdentificationUseCaseImpl(
            IBookingRepositoryPort repository,
            ClientValidator validator )
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public List<Booking> execute(String identification) {
        this._validator.validateGetByIdentification(identification);
        return _repository.getByClientIdentification(identification);
    }

}