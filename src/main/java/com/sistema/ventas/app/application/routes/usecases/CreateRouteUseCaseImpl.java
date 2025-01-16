package com.sistema.ventas.app.application.routes.usecases;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.in.ICreateRouteUseCase;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class CreateRouteUseCaseImpl implements ICreateRouteUseCase {

    private final IRouteRepositoryPort _repository;

    public  CreateRouteUseCaseImpl(IRouteRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public void execute(Route route) {
        this._repository.save(route);
    }
}
