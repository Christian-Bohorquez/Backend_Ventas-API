package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.in.IGetAllBillingsUseCase;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBillingsUseCaseImpl implements IGetAllBillingsUseCase {

    private final IBillingRepositoryPort _repository;

    public GetAllBillingsUseCaseImpl(IBillingRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public List<Billing> execute() {
        return _repository.getAll();
    }

}