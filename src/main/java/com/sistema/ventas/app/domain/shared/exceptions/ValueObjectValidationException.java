package com.sistema.ventas.app.domain.shared.exceptions;

public class ValueObjectValidationException extends DomainValidationException {

    public ValueObjectValidationException(String message) {
        super(message);
    }

}