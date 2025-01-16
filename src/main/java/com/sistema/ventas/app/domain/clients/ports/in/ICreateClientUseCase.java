package com.sistema.ventas.app.domain.clients.ports.in;

import com.sistema.ventas.app.domain.clients.models.Client;

public interface ICreateClientUseCase {
    void execute(Client client);
}
