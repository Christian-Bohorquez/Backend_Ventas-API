package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.application.payments.validators.PaymentValidator;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.in.IGetPaymentByIdUseCase;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetPaymentByIdUseCaseImpl implements IGetPaymentByIdUseCase {

    private final IPaymentRepositoryPort _repository;
    private final PaymentValidator _validator;

    public GetPaymentByIdUseCaseImpl(
            IPaymentRepositoryPort repository,
            PaymentValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Payment execute(UUID id) {
        _validator.validateGetById(id);
        return _repository.getById(id).get();
    }
}
