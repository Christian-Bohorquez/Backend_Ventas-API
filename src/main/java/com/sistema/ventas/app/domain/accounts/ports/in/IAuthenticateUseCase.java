package com.sistema.ventas.app.domain.accounts.ports.in;

import com.sistema.ventas.app.domain.accounts.models.Credential;

public interface IAuthenticateUseCase {
    String execute(Credential credential);
}
