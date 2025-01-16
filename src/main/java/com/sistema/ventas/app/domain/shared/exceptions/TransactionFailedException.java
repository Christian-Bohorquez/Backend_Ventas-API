package com.sistema.ventas.app.domain.shared.exceptions;

public class TransactionFailedException extends DomainValidationException {

    public TransactionFailedException(String message) {
        super(message);
    }

}
