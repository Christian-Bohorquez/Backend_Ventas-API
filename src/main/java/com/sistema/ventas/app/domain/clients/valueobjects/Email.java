package com.sistema.ventas.app.domain.clients.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import java.util.List;
import java.util.regex.Pattern;

public class Email {

    private final String value;

    // Expresión regular para validar formato de email
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    // Dominios prohibidos
    private static final List<String> BANNED_DOMAINS = List.of(
            "tempmail.com", "fakeemail.com", "mailinator.com"
    );

    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH = 6;

    private Email(String value) {
        this.value = value;
    }

    public static Email create(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValueObjectValidationException("El correo electrónico no puede estar vacío.");
        }

        if (value.contains(" ") || !value.equals(value.trim())) {
            throw new ValueObjectValidationException("El correo electrónico no puede contener espacios.");
        }

        if (value.length() < MIN_LENGTH) {
            throw new ValueObjectValidationException("El correo electrónico es demasiado corto.");
        }
        if (value.length() > MAX_LENGTH) {
            throw new ValueObjectValidationException("El correo electrónico es demasiado largo.");
        }

        if (!pattern.matcher(value).matches()) {
            throw new ValueObjectValidationException("El correo electrónico tiene un formato inválido.");
        }

        // **Validar dominio prohibido**
        String domain = value.substring(value.indexOf("@") + 1);
        if (BANNED_DOMAINS.contains(domain)) {
            throw new ValueObjectValidationException("El dominio del correo no está permitido.");
        }

        return new Email(value);
    }

    public String getValue() {
        return value;
    }
}
