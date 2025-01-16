package com.sistema.ventas.app.domain.agents.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

import java.util.regex.Pattern;

public class Password {

    private final String value;

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 15;
    private static final String PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$";
    private static final Pattern pattern = Pattern.compile(PATTERN);

    private Password(String value) {
        this.value = value;
    }

    public static Password create(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RequiredFieldMissingException("contraseña");
        }

        value = value.trim();

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new ValueObjectValidationException(
                    "La contraseña debe tener entre " + MIN_LENGTH + " y " + MAX_LENGTH + " caracteres"
            );
        }

        if (!pattern.matcher(value).matches()) {
            throw new ValueObjectValidationException(
                    "La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un número y un carácter especial"
            );
        }

        return new Password(value);
    }

    public String getValue() {
        return value;
    }

}
