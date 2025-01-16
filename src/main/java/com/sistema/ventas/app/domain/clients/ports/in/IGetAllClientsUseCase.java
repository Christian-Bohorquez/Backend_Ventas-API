package com.sistema.ventas.app.domain.clients.ports.in;

import com.sistema.ventas.app.domain.clients.models.Client;
import java.util.List;

public interface IGetAllClientsUseCase {
    List<Client> execute();
}
