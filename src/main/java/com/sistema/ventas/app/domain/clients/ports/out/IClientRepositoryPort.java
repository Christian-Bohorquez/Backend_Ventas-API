package com.sistema.ventas.app.domain.clients.ports.out;

import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.domain.shared.repositories.IRepository;

import java.util.Optional;

public interface IClientRepositoryPort extends IRepository<Client> {
    Optional<Client> getByIdentification(String identification);
    Optional<Client> getByEmail(String email);
}
