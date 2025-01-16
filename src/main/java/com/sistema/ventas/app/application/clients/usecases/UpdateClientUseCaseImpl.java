package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.in.IUpdateClientUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateClientUseCaseImpl implements IUpdateClientUseCase {

    private final IClientRepositoryPort _repository;
    private final ClientValidator _validator;

    public UpdateClientUseCaseImpl(
            IClientRepositoryPort repository,
            ClientValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Client client) {
        _validator.validateUpdate(client);
        _repository.save(client);
    }

}
