package com.sistema.ventas.app.domain.routes.ports.in;

import com.sistema.ventas.app.domain.routes.models.Route;

public interface ICreateRouteUseCase {
    void execute(Route route);
}
