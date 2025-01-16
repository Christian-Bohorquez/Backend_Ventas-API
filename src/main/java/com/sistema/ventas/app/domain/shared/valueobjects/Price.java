package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

public class Price {
    private final double value;

    private Price(double value) {
        this.value = truncateToTwoDecimals(value);
    }

    public static Price create(double value) {
        if (value <= 0) {
            throw new ValueObjectValidationException("El precio debe ser mayor a $0");
        }

        return new Price(value);
    }

    private static double truncateToTwoDecimals(double value) {
        return Math.floor(value * 100.0) / 100.0;
    }

    public double getValue() {
        return value;
    }
}
