package com.sistema.ventas.app.domain.accounts.ports.out;

import com.sistema.ventas.app.domain.accounts.models.Credential;

public interface IAccountRepositoryPort {
    String Authenticate(Credential credential);
    boolean existsByUsername(String username);
}
