package com.sistema.ventas.app.domain.agents.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

import java.util.regex.Pattern;

public class Username {

    private final String value;

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 20;
    private static final String PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]+$";
    private static final Pattern pattern = Pattern.compile(PATTERN);

    private Username(String value) {
        this.value = value;
    }

    public static Username create(String value, String fullName) {
        if (value == null || value.trim().isEmpty()) {
            throw new RequiredFieldMissingException("nombre de usuario");
        }

        value = value.trim();

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new ValueObjectValidationException(
                    "El nombre de usuario debe tener entre " + MIN_LENGTH + " y " + MAX_LENGTH + " caracteres."
            );
        }

        if (!pattern.matcher(value).matches()) {
            throw new ValueObjectValidationException(
                    "El nombre de usuario debe contener al menos una letra mayúscula, una letra minúscula y un número."
            );
        }

        if (fullName != null && !fullName.trim().isEmpty()) {
            String[] nameParts = fullName.split("\\s+");

            for (String part : nameParts) {
                if (value.toLowerCase().contains(part.toLowerCase())) {
                    throw new ValueObjectValidationException(
                            "El nombre de usuario no puede contener partes del nombre completo del usuario."
                    );
                }
            }
        }

        return new Username(value);
    }

    public String getValue() {
        return value;
    }

}
