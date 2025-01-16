package com.sistema.ventas.app.application.accounts.usecases;

import com.sistema.ventas.app.application.accounts.validators.AuthenticateValidator;
import com.sistema.ventas.app.domain.accounts.models.Credential;
import com.sistema.ventas.app.domain.accounts.ports.in.IAuthenticateUseCase;
import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.AuthenticationFailedException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUseCaseImpl implements IAuthenticateUseCase {

    private final IAccountRepositoryPort _repository;
    private final AuthenticateValidator _validator;

    public AuthenticateUseCaseImpl(
            IAccountRepositoryPort repository,
            AuthenticateValidator validator )
    {
        _repository = repository;
        _validator = validator;
    }

    @Override
    public String execute(Credential credential) {
        _validator.Validate(credential.getUsername());

        String token = _repository.Authenticate(credential);
        if (token == null || token.isEmpty()) {
            throw new AuthenticationFailedException("Credenciales incorrectas");
        }

        return token;
    }

}
