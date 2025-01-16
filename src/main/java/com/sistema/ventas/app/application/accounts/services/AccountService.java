package com.sistema.ventas.app.application.accounts.services;

import com.sistema.ventas.app.application.accounts.dtos.CredentialDto;
import com.sistema.ventas.app.domain.accounts.models.Credential;
import com.sistema.ventas.app.domain.accounts.ports.in.IAuthenticateUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final IAuthenticateUseCase _authenticateUseCase;

    public AccountService(IAuthenticateUseCase authenticateUseCase)
    {
        _authenticateUseCase = authenticateUseCase;
    }

    public ResponseAPI<String> Authenticate(CredentialDto dto)
    {
        var credentials = new Credential(dto.getUsername(), dto.getPassword());
        String token = _authenticateUseCase.execute(credentials);
        return new ResponseAPI(200, "Acceso autorizado", token);
    }

}
