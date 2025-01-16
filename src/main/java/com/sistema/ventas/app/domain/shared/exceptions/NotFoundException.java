package com.sistema.ventas.app.domain.shared.exceptions;

public class NotFoundException extends DomainValidationException {

    public NotFoundException(String entity) {
        super("No se encontró el/la " + entity + ".");
    }

}