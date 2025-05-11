package com.sistema.ventas.app.application.accounts.usecases;

import com.sistema.ventas.app.application.accounts.validators.AuthenticateValidator;
import com.sistema.ventas.app.domain.accounts.models.Credential;
import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.AuthenticationFailedException;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthenticateUseCaseImplTest {

    private IAccountRepositoryPort repository;
    private AuthenticateValidator validator;
    private AuthenticateUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        // Mock de las dependencias
        repository = Mockito.mock(IAccountRepositoryPort.class);
        validator = Mockito.mock(AuthenticateValidator.class);
        useCase = new AuthenticateUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidCredentials_ReturnsToken() {
        // Dado que tenemos credenciales válidas
        Credential credential = new Credential("existingUser", "correctPassword");
        String expectedToken = "validToken";

        // Comportamiento de los mocks
        try {
            // Usamos doNothing() porque Validate() es void
            doNothing().when(validator).Validate(credential.getUsername()); // Validador no lanza ninguna excepción
            when(repository.Authenticate(credential)).thenReturn(expectedToken); // El repositorio devuelve un token válido

            // Ejecutar el caso de uso
            String actualToken = useCase.execute(credential);

            // Verificar si el validador fue llamado
            verify(validator).Validate(credential.getUsername());
            // Verificar si el repositorio fue llamado para autenticar
            verify(repository).Authenticate(credential);

            // Verificar que el token devuelto es el esperado
            assertEquals(expectedToken, actualToken, "El token generado debe coincidir");
            System.out.println("Prueba exitosa: Se generó correctamente el token.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidCredentials_ReturnsToken: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidUsername_ThrowsNotFoundException() {
        // Dado un nombre de usuario inválido
        Credential credential = new Credential("nonexistentUser", "anyPassword");

        // El validador debe lanzar una excepción cuando el nombre de usuario no existe
        doThrow(new NotFoundException("nombre de usuario")).when(validator).Validate(credential.getUsername());

        try {
            // Intentamos ejecutar el caso de uso
            assertThrows(NotFoundException.class, () -> useCase.execute(credential));

            // Verificar si el validador fue llamado
            verify(validator).Validate(credential.getUsername());
            // Verificar que no se llame al repositorio para autenticar
            verify(repository, never()).Authenticate(credential);
            System.out.println("Prueba exitosa: Se lanzó NotFoundException por usuario inexistente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidUsername_ThrowsNotFoundException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_InvalidCredentials_ThrowsAuthenticationFailedException() {
        // Dado que tenemos credenciales incorrectas
        Credential credential = new Credential("existingUser", "wrongPassword");

        // El validador no lanza ninguna excepción, pero el repositorio no devuelve ningún token
        doNothing().when(validator).Validate(credential.getUsername()); // El validador no lanza una excepción
        when(repository.Authenticate(credential)).thenReturn(null); // El repositorio no devuelve un token

        try {
            // Intentamos ejecutar el caso de uso
            assertThrows(AuthenticationFailedException.class, () -> useCase.execute(credential));

            // Verificar si el validador fue llamado
            verify(validator).Validate(credential.getUsername());
            // Verificar que no se crea un token vacío
            verify(repository).Authenticate(credential);
            System.out.println("Prueba exitosa: Se lanzó AuthenticationFailedException por credenciales incorrectas.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_InvalidCredentials_ThrowsAuthenticationFailedException: " + e.getMessage());
            throw e;
        }
    }
}
