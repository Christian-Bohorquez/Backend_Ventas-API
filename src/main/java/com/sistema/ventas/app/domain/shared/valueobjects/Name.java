package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

import java.util.regex.Pattern;

public class Name {

    private final String value;

    private static final String NAME_PATTERN = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚñÑ]+)?$";
    private static final Pattern pattern = Pattern.compile(NAME_PATTERN);

    private Name(String value) {
        this.value = value;
    }

    public static Name create(String value, String fieldType) {
        if (value == null || value.trim().isEmpty()) {
            throw new RequiredFieldMissingException(fieldType);
        }

        value = value.trim();

        if (value.matches(".*\\d.*")) {
            throw new ValueObjectValidationException("El campo " + fieldType + " no puede contener números.");
        }

        if (!pattern.matcher(value).matches()) {
            throw new ValueObjectValidationException(
                    "El campo " + fieldType + " solo puede contener una o dos palabras."
            );
        }

        return new Name(value);
    }

    public String getValue() {
        return value;
    }
}
