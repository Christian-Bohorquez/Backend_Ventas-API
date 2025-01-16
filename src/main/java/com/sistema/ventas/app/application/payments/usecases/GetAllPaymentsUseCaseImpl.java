package com.sistema.ventas.app.application.payments.usecases;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.in.IGetAllPaymentsUseCase;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPaymentsUseCaseImpl implements IGetAllPaymentsUseCase {

    private final IPaymentRepositoryPort _repository;

    public GetAllPaymentsUseCaseImpl(IPaymentRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public List<Payment> execute() {
        return _repository.getAll();
    }

}
