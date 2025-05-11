package com.sistema.ventas.app.application.accounts.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.accounts.dtos.CredentialDto;
import com.sistema.ventas.app.domain.accounts.models.Credential;
import com.sistema.ventas.app.domain.accounts.ports.in.IAuthenticateUseCase;
import com.sistema.ventas.app.domain.shared.exceptions.AuthenticationFailedException;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountServiceTest {

    private IAuthenticateUseCase authenticateUseCase;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // Inicializar el mock del caso de uso de autenticación
        authenticateUseCase = mock(IAuthenticateUseCase.class);
        // Crear la instancia del servicio con el mock
        accountService = new AccountService(authenticateUseCase);
    }

    @Test
    void authenticate_ValidCredentials_ReturnsSuccessResponse() {
        // Mockear el DTO de credenciales
        CredentialDto dto = mock(CredentialDto.class);

        // Establecer los valores de username y password usando Mockito
        when(dto.getUsername()).thenReturn("validUser");
        when(dto.getPassword()).thenReturn("validPassword");

        String expectedToken = "validToken";

        // Configurar el comportamiento del mock: cuando se ejecute authenticateUseCase, devuelva el token
        when(authenticateUseCase.execute(any(Credential.class))).thenReturn(expectedToken);

        // Ejecutar el servicio con las credenciales
        ResponseAPI<String> response = accountService.Authenticate(dto);

        // Verificar que la respuesta sea correcta
        assertEquals(200, response.getStatusCode());
        assertEquals("Acceso autorizado", response.getMessage());
        assertEquals(expectedToken, response.getData());

        // Verificar que se llamó al caso de uso de autenticación con las credenciales
        verify(authenticateUseCase).execute(any(Credential.class));
        // Mensaje cuando el test pase correctamente
        System.out.println("Test authenticate_ValidCredentials_ReturnsSuccessResponse PASADO");
    }



    @Test
    void authenticate_InvalidCredentials_ThrowsException() {
        // Mockear el DTO de credenciales
        CredentialDto dto = mock(CredentialDto.class);

        // Establecer los valores de username y password usando Mockito
        when(dto.getUsername()).thenReturn("invalidUser");
        when(dto.getPassword()).thenReturn("wrongPassword");

        // Configurar el mock para lanzar una excepción de autenticación fallida
        when(authenticateUseCase.execute(any(Credential.class)))
                .thenThrow(new AuthenticationFailedException("Credenciales incorrectas"));

        // Verificar que se lanza la excepción al intentar autenticar con credenciales incorrectas
        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class, () -> {
            accountService.Authenticate(dto);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Credenciales incorrectas", exception.getMessage());

        // Verificar que no se llamó a devolver un token (ya que falló la autenticación)
        verify(authenticateUseCase).execute(any(Credential.class));

        // Mensaje cuando el test pase correctamente
        System.out.println("Test authenticate_InvalidCredentials_ThrowsException PASADO");
    }

    @Test
    void authenticate_UsernameNotFound_ThrowsNotFoundException() {
        // Mockear el DTO de credenciales
        CredentialDto dto = mock(CredentialDto.class);

        // Establecer los valores de username y password usando Mockito
        when(dto.getUsername()).thenReturn("nonExistentUser");
        when(dto.getPassword()).thenReturn("anyPassword");

        // Configurar el mock para lanzar una excepción de usuario no encontrado
        when(authenticateUseCase.execute(any(Credential.class)))
                .thenThrow(new NotFoundException("No se encontró el/la nombre de usuario"));

        // Verificar que se lanza la excepción de NotFoundException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.Authenticate(dto);
        });

        // Verificar que el mensaje contiene la subcadena esperada
        assertTrue(exception.getMessage().contains("No se encontró el/la nombre de usuario"));

        // Verificar que se llamó al caso de uso de autenticación
        verify(authenticateUseCase).execute(any(Credential.class));

        // Mensaje cuando el test pase correctamente
        System.out.println("Test authenticate_UsernameNotFound_ThrowsNotFoundException PASADO");
    }


}