package com.sistema.ventas.app.application.billings.usecases;

import com.sistema.ventas.app.application.billings.validators.BillingValidator;
import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.in.ICreateBillingUseCase;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CreateBillingUseCaseImpl implements ICreateBillingUseCase {

    private final IBillingRepositoryPort _repository;
    private final BillingValidator _validator;

    public CreateBillingUseCaseImpl(
            IBillingRepositoryPort repository,
            BillingValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Billing billing) {
        _validator.validateCreate(billing);
        _repository.save(billing);
    }

}
