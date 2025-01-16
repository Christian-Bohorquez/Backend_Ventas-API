package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.in.IGetClientByIdUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetClientByIdUseCaseImpl implements IGetClientByIdUseCase {

    private final IClientRepositoryPort _repository;
    private final ClientValidator _validator;

    public GetClientByIdUseCaseImpl(
            IClientRepositoryPort repository,
            ClientValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Client execute(UUID id) {
        _validator.validateGetById(id);
        return _repository.getById(id).get();
    }

}
