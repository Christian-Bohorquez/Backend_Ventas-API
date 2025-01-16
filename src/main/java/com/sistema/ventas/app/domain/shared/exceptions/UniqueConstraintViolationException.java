package com.sistema.ventas.app.domain.shared.exceptions;

public class UniqueConstraintViolationException extends DomainValidationException
{
    private final String propertyValue;

    public UniqueConstraintViolationException(String propertyValue) {
        super(propertyValue + " ya está registrado");
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }
}