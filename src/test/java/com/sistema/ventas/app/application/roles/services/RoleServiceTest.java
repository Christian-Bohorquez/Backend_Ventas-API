package com.sistema.ventas.app.application.roles.services;

import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.roles.dtos.RoleCreateDto;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.in.ICreateRoleUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    private ICreateRoleUseCase createRoleUseCase;
    private RoleService service;

    @BeforeEach
    void setup() {
        createRoleUseCase = Mockito.mock(ICreateRoleUseCase.class);
        service = new RoleService(createRoleUseCase);
    }

    @Test
    void createRole_ValidDto_ReturnsSuccessResponse() {
        // Crear el DTO
        RoleCreateDto dto = new RoleCreateDto();

        // Usamos reflexión para cambiar el valor de 'name' (campo privado)
        try {
            Field nameField = RoleCreateDto.class.getDeclaredField("name");
            nameField.setAccessible(true);  // Permitir el acceso a un campo privado
            nameField.set(dto, "Admin");  // Asignar el valor al campo privado 'name'
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Ejecutar el método del servicio
        try {
            ResponseAPI response = service.createRole(dto);

            // Verificar la respuesta
            assertEquals(201, response.getStatusCode());
            assertEquals("Rol creado exitosamente", response.getMessage());

            // Verificar que el método de ejecución del use case fue llamado correctamente
            verify(createRoleUseCase).execute(argThat(role -> "Admin".equals(role.getName())));
            System.out.println("Prueba exitosa: Rol creado con éxito a partir del DTO.");
        } catch (AssertionError e) {
            System.out.println("Error en createRole_ValidDto_ReturnsSuccessResponse: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void createRole_DuplicateRole_ReturnsError() {
        // Crear el DTO mockeado
        RoleCreateDto dto = mock(RoleCreateDto.class);

        // Configurar el mock para devolver un nombre válido
        when(dto.getName()).thenReturn("Admin");

        // Simular un error de duplicado en el caso de uso
        doThrow(new IllegalArgumentException("El nombre del rol ya existe"))
                .when(createRoleUseCase).execute(any(Role.class));

        // Ejecutar el método del servicio
        ResponseAPI response = service.createRole(dto);

        // Verificar la respuesta
        assertEquals(400, response.getStatusCode());
        assertEquals("El nombre del rol ya existe", response.getMessage());

        // Verificar que el método de ejecución del use case fue llamado
        verify(createRoleUseCase).execute(argThat(role -> "Admin".equals(role.getName())));
    }


    @Test
    void createRole_EmptyName_ReturnsError() {
        // Crear el DTO mockeado
        RoleCreateDto dto = mock(RoleCreateDto.class);

        // Configurar el mock para devolver un nombre vacío
        when(dto.getName()).thenReturn("");

        // Ejecutar el método del servicio
        ResponseAPI response = service.createRole(dto);

        // Verificar la respuesta
        assertEquals(400, response.getStatusCode());
        assertEquals("El nombre del rol no puede estar vacío", response.getMessage());

        // Verificar que el método de ejecución del use case no fue llamado
        verify(createRoleUseCase, times(0)).execute(any());
    }


    @Test
    void createRole_NullName_ReturnsError() {
        // Crear el DTO mockeado
        RoleCreateDto dto = mock(RoleCreateDto.class);

        // Configurar el mock para devolver un nombre nulo
        when(dto.getName()).thenReturn(null);

        // Ejecutar el método del servicio
        ResponseAPI response = service.createRole(dto);

        // Verificar la respuesta
        assertEquals(400, response.getStatusCode());
        assertEquals("El nombre del rol no puede ser nulo", response.getMessage());

        // Verificar que el método de ejecución del use case no fue llamado
        verify(createRoleUseCase, times(0)).execute(any());
    }



    @Test
    void createRole_ValidDto_UseCaseCalledOnce() {
        // Crear el DTO
        RoleCreateDto dto = new RoleCreateDto();

        // Usamos reflexión para cambiar el valor de 'name' (campo privado)
        try {
            Field nameField = RoleCreateDto.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(dto, "User");  // Asignamos un nombre válido
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Ejecutar el método del servicio
        service.createRole(dto);

        // Verificar que el método de ejecución del use case fue llamado exactamente una vez
        verify(createRoleUseCase, times(1)).execute(argThat(role -> "User".equals(role.getName())));
    }
}
