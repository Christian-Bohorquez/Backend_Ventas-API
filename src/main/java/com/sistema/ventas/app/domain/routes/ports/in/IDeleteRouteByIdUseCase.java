package com.sistema.ventas.app.domain.routes.ports.in;

import java.util.UUID;

public interface IDeleteRouteByIdUseCase {
    void execute(UUID id);
}
