package com.sistema.ventas.app.application.routes.usecases;

import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.in.IGetRouteByIdUseCase;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class GetRouteByIdUseCaseImpl implements IGetRouteByIdUseCase {

    private final IRouteRepositoryPort _repository;
    private final RouteValidator _validator;

    public  GetRouteByIdUseCaseImpl(
            IRouteRepositoryPort repository,
            RouteValidator validator )
    {
        this._repository = repository;
        this._validator = validator;
    }

    @Override
    public Route execute(UUID id) {
        _validator.validateGetById(id);
        return  _repository.getById(id).get();
    }
}
