package com.sistema.ventas.app.application.routes.validators;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.Optional;

public class RouteValidatorTest {

    private IRouteRepositoryPort routeRepository;
    private RouteValidator routeValidator;

    @BeforeEach
    void setup() {
        routeRepository = mock(IRouteRepositoryPort.class);
        routeValidator = new RouteValidator(routeRepository);
    }

    @Test
    void validateGetById_RouteNotFound_ThrowsNotFoundException() {
        UUID routeId = UUID.randomUUID();

        // GIVEN: El repositorio no devuelve una ruta
        when(routeRepository.getById(routeId)).thenReturn(Optional.empty());

        // WHEN & THEN: Se espera una excepción NotFoundException con el mensaje correcto
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            routeValidator.validateGetById(routeId);
        });

        assertEquals("No se encontró el/la Ruta.", exception.getMessage());

        verify(routeRepository, times(1)).getById(routeId); // Verifica que se consultó en el repositorio

        System.out.println("PRUEBA ÉXITO: Se lanzó NotFoundException al validar una ruta inexistente.");
    }

    @Test
    void validateGetById_RouteFound_NoExceptionThrown() {
        UUID routeId = UUID.randomUUID();
        Route route = mock(Route.class);

        // GIVEN: El repositorio devuelve una ruta válida
        when(routeRepository.getById(routeId)).thenReturn(Optional.of(route));

        // WHEN & THEN: No se lanza ninguna excepción
        assertDoesNotThrow(() -> routeValidator.validateGetById(routeId));

        verify(routeRepository, times(1)).getById(routeId);  // Verifica que se llame al repositorio

        System.out.println("PRUEBA ÉXITO: Se validó correctamente una ruta existente.");
    }
}