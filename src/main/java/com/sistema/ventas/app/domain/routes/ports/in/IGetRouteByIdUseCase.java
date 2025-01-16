package com.sistema.ventas.app.domain.routes.ports.in;

import com.sistema.ventas.app.domain.routes.models.Route;

import java.util.UUID;

public interface IGetRouteByIdUseCase {
    Route execute(UUID id);
}
