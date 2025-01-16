package com.sistema.ventas.app.domain.routes.ports.in;

import com.sistema.ventas.app.domain.routes.models.Route;

import java.util.List;

public interface IGetAllRoutesUseCase {
    List<Route> execute();
}
