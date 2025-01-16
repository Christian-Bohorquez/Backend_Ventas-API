package com.sistema.ventas.app.domain.clients.ports.in;

import java.util.UUID;

public interface IDeleteClientByIdUseCase {
    void execute(UUID id);
}
