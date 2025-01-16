package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.application.bookings.validators.BookingValidator;
import com.sistema.ventas.app.domain.billings.ports.in.IDeleteBillingByIdUseCase;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteBillingByIdUseCaseImpl implements IDeleteBillingByIdUseCase {

    private final IBillingRepositoryPort _repository;
    private final BookingValidator _validator;

    public DeleteBillingByIdUseCaseImpl(
            IBillingRepositoryPort repository,
            BookingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(UUID id) {
        _validator.validateGetById(id);
        _repository.delete(id);
    }
}
