package com.sistema.ventas.app.application.routes.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class DeleteRouteByIdUseCaseImplTest {
    @Mock
    private IRouteRepositoryPort repository;

    @Mock
    private RouteValidator validator;

    private DeleteRouteByIdUseCaseImpl deleteRouteByIdUseCase;

    private UUID routeId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteRouteByIdUseCase = new DeleteRouteByIdUseCaseImpl(repository, validator);
        routeId = UUID.randomUUID();
    }

    @Test
    void execute_ValidRoute_DeletesRoute() {
        // Arrange
        doNothing().when(validator).validateGetById(routeId);
        doNothing().when(repository).delete(routeId);

        // Act
        deleteRouteByIdUseCase.execute(routeId);

        // Assert
        verify(validator, times(1)).validateGetById(routeId);
        verify(repository, times(1)).delete(routeId);
        System.out.println("Test 'execute_ValidRoute_DeletesRoute' passed successfully.");
    }

    @Test
    void execute_InvalidRoute_ThrowsException() {
        // Arrange
        doThrow(new IllegalArgumentException("Ruta no válida")).when(validator).validateGetById(routeId);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteRouteByIdUseCase.execute(routeId);
        });

        assertEquals("Ruta no válida", exception.getMessage());
        verify(repository, never()).delete(routeId);
        System.out.println("Test 'execute_InvalidRoute_ThrowsException' passed successfully.");
    }
}