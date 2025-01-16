package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.in.ICreateClientUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CreateClientUseCaseImpl implements ICreateClientUseCase {

    private final IClientRepositoryPort _repository;
    private final ClientValidator _validator;

    public CreateClientUseCaseImpl(
            IClientRepositoryPort repository,
            ClientValidator validator)
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public void execute(Client client) {
        _validator.validateCreate(client);
        _repository.save(client);
    }
}
