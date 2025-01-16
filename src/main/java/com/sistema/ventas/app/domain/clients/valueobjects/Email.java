package com.sistema.ventas.app.domain.clients.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

import java.util.regex.Pattern;

public class Email {

    private final String value;
    private static final String EMAIL_PATTERN = "^[^@]+@(gmail\\.com|hotmail\\.com|outlook\\.com)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private Email(String value) { this.value = value; }

    public static Email create(String value) {
        if (value == null || value.isEmpty() || !pattern.matcher(value).matches()) {
            throw new ValueObjectValidationException("El campo correo electrónico tiene un formato inválido");
        }
        return new Email(value);
    }

    public String getValue() {
        return value;
    }

}
