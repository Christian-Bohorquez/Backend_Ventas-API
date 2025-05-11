package com.sistema.ventas.app.application.roles.usecases;

import com.sistema.ventas.app.application.roles.validators.RoleValidator;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateRoleUseCaseImplTest {

    private IRoleRepositoryPort repository;
    private RoleValidator validator;
    private CreateRoleUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IRoleRepositoryPort.class);
        validator = Mockito.mock(RoleValidator.class);
        useCase = new CreateRoleUseCaseImpl(repository, validator);
    }

    @Test
    void execute_ValidRole_SavesRole() {
        Role role = new Role("Admin");

        try {
            useCase.execute(role);
            verify(validator).validateCreate(role);
            verify(repository).save(role);
            System.out.println("Prueba exitosa: Rol válido creado correctamente.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_ValidRole_SavesRole: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void execute_DuplicateRole_ThrowsException() {
        Role role = new Role("Admin");
        doThrow(new RuntimeException("El nombre ya está registrado.")).when(validator).validateCreate(role);

        try {
            Exception exception = assertThrows(RuntimeException.class, () -> useCase.execute(role));
            assertEquals("El nombre ya está registrado.", exception.getMessage());
            verify(validator).validateCreate(role);
            verify(repository, never()).save(role);
            System.out.println("Prueba exitosa: Se detectó correctamente un conflicto al crear un rol duplicado.");
        } catch (AssertionError e) {
            System.out.println("Error en execute_DuplicateRole_ThrowsException: " + e.getMessage());
            throw e;
        }
    }
}
