package com.sistema.ventas.app.application.roles.validators;

import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.UniqueConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleValidatorTest {

    private final IRoleRepositoryPort repository = mock(IRoleRepositoryPort.class);
    private final RoleValidator validator = new RoleValidator(repository);

    @Test
    void validateCreate_NameExists_ThrowsException() {
        // GIVEN
        Role role = new Role("Admin");
        when(repository.getByName("Admin")).thenReturn(Optional.of(role));

        // WHEN & THEN
        try {
            UniqueConstraintViolationException exception = assertThrows(
                    UniqueConstraintViolationException.class,
                    () -> validator.validateCreate(role)
            );
            assertEquals("El nombre ya está registrado", exception.getMessage());
            verify(repository).getByName("Admin");
            System.out.println("Prueba exitosa: Se detectó correctamente un nombre de rol duplicado.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_NameExists_ThrowsException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void validateCreate_NameDoesNotExist_Success() {
        // GIVEN
        Role role = new Role("Admin");
        when(repository.getByName("Admin")).thenReturn(Optional.empty());

        // WHEN & THEN
        try {
            assertDoesNotThrow(() -> validator.validateCreate(role));
            verify(repository).getByName("Admin");
            System.out.println("Prueba exitosa: No se detectaron conflictos al validar un rol con nombre único.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_NameDoesNotExist_Success: " + e.getMessage());
            throw e;
        }
    }

    // Prueba adicional: Si el rol ya existe, no se lanza excepción
    @Test
    void validateCreate_RoleAlreadyExists_Success() {
        // GIVEN
        Role role = new Role("Admin");
        when(repository.getByName("Admin")).thenReturn(Optional.empty());  // No existe el rol

        // WHEN & THEN
        try {
            assertDoesNotThrow(() -> validator.validateCreate(role));
            verify(repository).getByName("Admin");
            System.out.println("Prueba exitosa: Se validó correctamente un rol que no existe.");
        } catch (AssertionError e) {
            System.out.println("Error en validateCreate_RoleAlreadyExists_Success: " + e.getMessage());
            throw e;
        }
    }
}