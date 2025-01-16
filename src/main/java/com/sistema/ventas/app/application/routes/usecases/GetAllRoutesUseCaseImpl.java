package com.sistema.ventas.app.application.routes.usecases;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.in.IGetAllRoutesUseCase;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetAllRoutesUseCaseImpl implements IGetAllRoutesUseCase {

    private final IRouteRepositoryPort _repository;

    public  GetAllRoutesUseCaseImpl(IRouteRepositoryPort repository)
    {
        this._repository = repository;
    }

    @Override
    public List<Route> execute() {
        return this._repository.getAll();
    }
}
