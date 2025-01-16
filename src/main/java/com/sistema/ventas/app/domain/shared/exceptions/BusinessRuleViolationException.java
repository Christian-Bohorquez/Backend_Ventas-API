package com.sistema.ventas.app.domain.shared.exceptions;

public class BusinessRuleViolationException extends DomainValidationException {

    public BusinessRuleViolationException(String message) {
        super(message);
    }

}
