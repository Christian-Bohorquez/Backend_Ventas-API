package com.sistema.ventas.app.domain.bookings.utils;

import java.util.Random;

public class BookingReferenceGenerator {

    private static final int LETTERS_COUNT = 5;
    private static final int NUMBERS_COUNT = 5;

    private BookingReferenceGenerator() { }

    public static String generate() {
        String lettersPart = generateRandomLetters(LETTERS_COUNT);
        String numbersPart = generateRandomNumbers(NUMBERS_COUNT);
        return lettersPart + "-" + numbersPart;
    }

    private static String generateRandomLetters(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser positivo");
        }

        StringBuilder letters = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            letters.append(letter);
        }

        return letters.toString();
    }

    private static String generateRandomNumbers(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser positivo");
        }

        StringBuilder numbers = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int digit = random.nextInt(10);
            numbers.append(digit);
        }

        return numbers.toString();
    }
}
