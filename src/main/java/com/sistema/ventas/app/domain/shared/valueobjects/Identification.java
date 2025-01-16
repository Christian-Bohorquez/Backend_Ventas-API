package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;

public class Identification {

    private final String value;

    private Identification(String value) {
        this.value = value;
    }

    public static Identification create(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new RequiredFieldMissingException("identificación");
        }

        value = value.trim();

        if (!isAllDigits(value)) {
            throw new ValueObjectValidationException("El campo identificación solo puede contener dígitos.");
        }

        if (value.length() != 10 || !isValidEcuadorianId(value)) {
            throw new ValueObjectValidationException("El campo identificación es un número inválido.");
        }

        return new Identification(value);
    }

    private static boolean isValidEcuadorianId(String cedula) {
        int[] digits = new int[10];

        try {
            for (int i = 0; i < 10; i++) {
                digits[i] = Character.getNumericValue(cedula.charAt(i));
            }
        } catch (NumberFormatException e) {
            return false;
        }

        int provinceCode = Integer.parseInt(cedula.substring(0, 2));
        if ((provinceCode < 1 || provinceCode > 24) && provinceCode != 30) {
            return false;
        }

        if (digits[2] < 0 || digits[2] > 6) {
            return false;
        }

        int sumOdd = 0;
        int sumEven = 0;

        for (int i = 0; i < 9; i += 2) {
            int result = digits[i] * 2;
            if (result > 9) result -= 9;
            sumOdd += result;
        }

        for (int i = 1; i < 8; i += 2) {
            sumEven += digits[i];
        }

        int totalSum = sumOdd + sumEven;
        int nextTen = (int) Math.ceil(totalSum / 10.0) * 10;
        int verifier = nextTen - totalSum;

        return verifier == digits[9];
    }

    private static boolean isAllDigits(String value) {
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public String getValue() {
        return value;
    }

}
