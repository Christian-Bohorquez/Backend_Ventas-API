package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.application.payments.validators.PaymentValidator;
import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.in.ICreatePaymentUseCase;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CreatePaymentUseCaseImpl implements ICreatePaymentUseCase {

    private final IPaymentRepositoryPort _repository;
    private final PaymentValidator _validator;

    public CreatePaymentUseCaseImpl(
            IPaymentRepositoryPort repository,
            PaymentValidator validator )
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Payment payment) {
        _validator.validateCreate(payment);
        _repository.save(payment);
    }

}
