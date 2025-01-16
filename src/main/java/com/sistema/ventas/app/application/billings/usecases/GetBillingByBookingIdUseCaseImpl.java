package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.application.billings.validators.BillingValidator;
import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.in.IGetBillingByBookingIdUseCase;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetBillingByBookingIdUseCaseImpl implements IGetBillingByBookingIdUseCase {

    private final IBillingRepositoryPort _repository;
    private final BookingValidator _validator;

    public GetBillingByBookingIdUseCaseImpl(
            IBillingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Billing execute(UUID bookingId) {
        _validator.validateGetById(bookingId);
        return _repository.getByBookingId(bookingId).get();
    }

}
