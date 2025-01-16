package com.sistema.ventas.app.domain.shared.exceptions;

public class RequiredFieldMissingException extends DomainValidationException {

    public RequiredFieldMissingException(String fieldName) {
        super("El campo '" + fieldName + "' es obligatorio");
    }

}