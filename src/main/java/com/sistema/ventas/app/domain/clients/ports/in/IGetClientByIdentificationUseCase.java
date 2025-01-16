package com.sistema.ventas.app.domain.clients.ports.in;

import com.sistema.ventas.app.domain.clients.models.Client;

public interface IGetClientByIdentificationUseCase {
    Client execute(String identification);
}
