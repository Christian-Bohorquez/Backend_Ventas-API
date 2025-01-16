package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.application.clients.validators.ClientValidator;
import com.sistema.ventas.app.domain.clients.ports.in.IDeleteClientByIdUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteClientByIdUseCaseImpl implements IDeleteClientByIdUseCase {

    private final IClientRepositoryPort _repository;
    private final ClientValidator _validator;

    public DeleteClientByIdUseCaseImpl(
            IClientRepositoryPort repository,
            ClientValidator validator)
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
