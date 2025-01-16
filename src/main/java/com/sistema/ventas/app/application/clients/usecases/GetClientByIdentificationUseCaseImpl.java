package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.in.IGetClientByIdentificationUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class GetClientByIdentificationUseCaseImpl implements IGetClientByIdentificationUseCase {

    private final IClientRepositoryPort _repository;
    private final ClientValidator _validator;

    public GetClientByIdentificationUseCaseImpl(
            IClientRepositoryPort repository,
            ClientValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Client execute(String identification) {
        _validator.validateGetByIdentification(identification);
        return _repository.getByIdentification(identification).get();
    }

}
