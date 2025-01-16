package com.sistema.ventas.app.domain.shared.exceptions;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String message) {
        super(message);
    }
    
}
