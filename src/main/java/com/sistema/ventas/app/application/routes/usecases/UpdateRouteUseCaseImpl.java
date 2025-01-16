package com.sistema.ventas.app.application.routes.usecases;

import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.in.IUpdateRouteUseCase;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class UpdateRouteUseCaseImpl implements IUpdateRouteUseCase {

    private final IRouteRepositoryPort _repository;
    private final RouteValidator _validator;

    public  UpdateRouteUseCaseImpl(
            IRouteRepositoryPort repository,
            RouteValidator validator )
    {
        this._repository = repository;
        this._validator = validator;
    }
    @Override
    public void execute(Route route) {
        _validator.validateGetById(route.getId());
        _repository.save(route);
    }
}
