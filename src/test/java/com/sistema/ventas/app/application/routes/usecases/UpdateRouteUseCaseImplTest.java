package com.sistema.ventas.app.application.routes.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import com.sistema.ventas.app.application.routes.usecases.UpdateRouteUseCaseImpl;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

public class UpdateRouteUseCaseImplTest {

    @Mock
    private IRouteRepositoryPort repository;

    @Mock
    private RouteValidator validator;

    private UpdateRouteUseCaseImpl updateRouteUseCase;

    private UUID routeId;
    private Route route;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        updateRouteUseCase = new UpdateRouteUseCaseImpl(repository, validator);
        routeId = UUID.randomUUID();

        // Usamos un mock para la clase Route
        route = mock(Route.class);

        // Creamos un objeto Price para simular el precio
        Price price = Price.create(100.0);

        // Configuramos el comportamiento esperado
        when(route.getId()).thenReturn(routeId);
        when(route.getStartLocation()).thenReturn("Start");
        when(route.getEndLocation()).thenReturn("End");
        when(route.getPrice()).thenReturn(price);
    }

    @Test
    void execute_ValidRoute_UpdatesRoute() {
        // Arrange
        doNothing().when(validator).validateGetById(routeId); // Simulamos que no se lanza excepción
        doNothing().when(repository).save(route); // Simulamos que el repositorio guarda la ruta

        // Act
        updateRouteUseCase.execute(route);

        // Assert
        verify(validator, times(1)).validateGetById(routeId);
        verify(repository, times(1)).save(route);

        System.out.println("PRUEBA ÉXITO: Se actualizó correctamente la ruta.");
    }
    @Test
    void execute_InvalidRoute_ThrowsException() {
        // Arrange
        doThrow(new IllegalArgumentException("Ruta no válida")).when(validator).validateGetById(routeId); // Simulamos que se lanza excepción

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            updateRouteUseCase.execute(route);
        });

        assertEquals("Ruta no válida", exception.getMessage());
        verify(repository, never()).save(route);

        System.out.println("PRUEBA ÉXITO: Se lanzó una excepción al intentar actualizar una ruta inválida.");
    }
}