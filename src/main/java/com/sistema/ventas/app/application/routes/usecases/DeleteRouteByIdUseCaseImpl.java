package com.sistema.ventas.app.application.routes.usecases;

import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import com.sistema.ventas.app.domain.routes.ports.in.IDeleteRouteByIdUseCase;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DeleteRouteByIdUseCaseImpl implements IDeleteRouteByIdUseCase {

    private final IRouteRepositoryPort _repository;
    private final RouteValidator _validator;

    public  DeleteRouteByIdUseCaseImpl(
            IRouteRepositoryPort repository,
            RouteValidator validator )
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
