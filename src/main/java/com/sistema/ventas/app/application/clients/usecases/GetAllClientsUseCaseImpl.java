package com.sistema.ventas.app.application.clients.usecases;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.clients.ports.in.IGetAllClientsUseCase;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllClientsUseCaseImpl implements IGetAllClientsUseCase {

    private final IClientRepositoryPort _repository;

    public GetAllClientsUseCaseImpl(IClientRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public List<Client> execute() {
        return _repository.getAll();
    }

}
