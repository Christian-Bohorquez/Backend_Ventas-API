package com.sistema.ventas.app.application.accounts.validators;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthenticateValidatorTest {

    private IAccountRepositoryPort accountRepository;
    private AuthenticateValidator validator;

    @BeforeEach
    void setup() {
        accountRepository = Mockito.mock(IAccountRepositoryPort.class);
        validator = new AuthenticateValidator(accountRepository);
    }

    @Test
    void validate_UsernameNotFound_ThrowsNotFoundException() {
        // GIVEN
        String username = "nonExistentUser";

        // Mock para simular que el nombre de usuario no existe en el repositorio
        when(accountRepository.existsByUsername(username)).thenReturn(false);

        // WHEN & THEN
        try {
            NotFoundException exception = assertThrows(
                    NotFoundException.class,
                    () -> validator.Validate(username)
            );
            // Cambiar el mensaje esperado para coincidir con el mensaje real
            assertEquals("No se encontró el/la nombre de usuario.", exception.getMessage());
            verify(accountRepository, times(1)).existsByUsername(username);
            System.out.println("Prueba exitosa: Se detectó correctamente que el nombre de usuario no existe.");
        } catch (AssertionError e) {
            System.out.println("Error en validate_UsernameNotFound_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void validate_UsernameFound_Success() {
        // GIVEN
        String username = "validUser";

        // Mock para simular que el nombre de usuario existe en el repositorio
        when(accountRepository.existsByUsername(username)).thenReturn(true);

        // WHEN & THEN
        try {
            assertDoesNotThrow(() -> validator.Validate(username));
            verify(accountRepository, times(1)).existsByUsername(username);
            System.out.println("Prueba exitosa: El nombre de usuario fue validado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en validate_UsernameFound_Success: " + e.getMessage());
            throw e;
        }
    }
}
