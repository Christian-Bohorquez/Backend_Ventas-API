package com.sistema.ventas.app.application.routes.usecases;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class GetRouteByIdUseCaseImplTest {
    @Mock
    private IRouteRepositoryPort repository;

    @Mock
    private RouteValidator validator;

    private GetRouteByIdUseCaseImpl getRouteByIdUseCase;

    private UUID routeId;
    private Route route;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getRouteByIdUseCase = new GetRouteByIdUseCaseImpl(repository, validator);
        routeId = UUID.randomUUID();
        route = mock(Route.class);
    }

    @Test
    void execute_ValidRoute_ReturnsRoute() {
        // Arrange
        doNothing().when(validator).validateGetById(routeId);
        when(repository.getById(routeId)).thenReturn(Optional.of(route));

        // Act
        Route result = getRouteByIdUseCase.execute(routeId);

        // Assert
        assertEquals(route, result);
        verify(validator, times(1)).validateGetById(routeId);
        verify(repository, times(1)).getById(routeId);
        System.out.println("Test 'execute_ValidRoute_ReturnsRoute' passed successfully.");
    }

    @Test
    void execute_RouteNotFound_ThrowsException() {
        // Arrange
        doNothing().when(validator).validateGetById(routeId);
        when(repository.getById(routeId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            getRouteByIdUseCase.execute(routeId);
        });

        assertEquals("No value present", exception.getMessage());  // El mensaje esperado de NoSuchElementException
        verify(validator, times(1)).validateGetById(routeId);
        verify(repository, times(1)).getById(routeId);
        System.out.println("Test 'execute_RouteNotFound_ThrowsException' passed successfully.");
    }

}