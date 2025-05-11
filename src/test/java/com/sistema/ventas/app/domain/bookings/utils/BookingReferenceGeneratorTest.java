package com.sistema.ventas.app.domain.bookings.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class BookingReferenceGeneratorTest {

    @Test
    @DisplayName("Debe generar una referencia válida con el formato correcto")
    void shouldGenerateValidReference() {
        String reference = BookingReferenceGenerator.generate();

        // Verificar que no sea nulo ni vacío
        assertNotNull(reference);
        assertFalse(reference.isEmpty(), "La referencia no debe estar vacía");

        // Verificar el formato de la referencia
        assertTrue(reference.matches("^[A-Z]{5}-\\d{5}$"), "Formato incorrecto de la referencia");
    }

    @Test
    @DisplayName("Debe lanzar una excepción si el tamaño de letras es inválido")
    void shouldThrowExceptionForInvalidLetterSize() throws Exception {
        var method = BookingReferenceGenerator.class.getDeclaredMethod("generateRandomLetters", int.class);
        method.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(null, 0));

        // Extraer la causa real de la excepción
        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, cause);
        assertEquals("El tamaño debe ser positivo", cause.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar una excepción si el tamaño de números es inválido")
    void shouldThrowExceptionForInvalidNumberSize() throws Exception {
        var method = BookingReferenceGenerator.class.getDeclaredMethod("generateRandomNumbers", int.class);
        method.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, () -> method.invoke(null, -1));

        // Extraer la causa real de la excepción
        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, cause);
        assertEquals("El tamaño debe ser positivo", cause.getMessage());
    }


    @Test
    @DisplayName("Debe generar letras aleatorias válidas")
    void shouldGenerateValidRandomLetters() throws Exception {
        var method = BookingReferenceGenerator.class.getDeclaredMethod("generateRandomLetters", int.class);
        method.setAccessible(true);

        String letters = (String) method.invoke(null, 5);
        assertNotNull(letters);
        assertEquals(5, letters.length());
        assertTrue(letters.matches("^[A-Z]{5}$"));
    }

    @Test
    @DisplayName("Debe generar números aleatorios válidos")
    void shouldGenerateValidRandomNumbers() throws Exception {
        var method = BookingReferenceGenerator.class.getDeclaredMethod("generateRandomNumbers", int.class);
        method.setAccessible(true);

        String numbers = (String) method.invoke(null, 5);
        assertNotNull(numbers);
        assertEquals(5, numbers.length());
        assertTrue(numbers.matches("^\\d{5}$"));
    }
}
