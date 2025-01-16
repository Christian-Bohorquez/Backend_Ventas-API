package com.sistema.ventas.app.application.routes.validators;

import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RouteValidator {

    private final IRouteRepositoryPort _routeRepository;

    public RouteValidator(IRouteRepositoryPort routeRepository)
    {
        this._routeRepository = routeRepository;
    }

    public void validateGetById(UUID id) {
        _routeRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Ruta"));
    }

}
