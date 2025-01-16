package com.sistema.ventas.app.application.accounts.validators;

import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateValidator {

    private final IAccountRepositoryPort _accountRepository;

    public AuthenticateValidator(IAccountRepositoryPort accountRepository)
    {
        _accountRepository = accountRepository;
    }

    public void Validate(String username)
    {
        if (!_accountRepository.existsByUsername(username))
        {
            throw new NotFoundException("nombre de usuario");
        }
    }
}
