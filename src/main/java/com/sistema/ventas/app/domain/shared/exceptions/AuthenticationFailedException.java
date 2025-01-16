package com.sistema.ventas.app.domain.shared.exceptions;

public class AuthenticationFailedException extends DomainValidationException {

    public AuthenticationFailedException(String message) {
        super(message);
    }

}