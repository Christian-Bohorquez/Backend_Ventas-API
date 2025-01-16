package com.sistema.ventas.app.domain.clients.ports.in;

import com.sistema.ventas.app.domain.clients.models.Client;

import java.util.UUID;

public interface IGetClientByIdUseCase {
    Client execute(UUID id);
}
