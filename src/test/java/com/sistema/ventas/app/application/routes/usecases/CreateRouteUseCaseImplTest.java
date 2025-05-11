package com.sistema.ventas.app.application.routes.usecases;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

public class CreateRouteUseCaseImplTest {

    @Mock
    private IRouteRepositoryPort repository;

    private CreateRouteUseCaseImpl createRouteUseCase;

    private Route route;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        createRouteUseCase = new CreateRouteUseCaseImpl(repository);
        route = mock(Route.class);
    }

    @Test
    void execute_ValidRoute_CreatesRoute() {
        // Arrange
        doNothing().when(repository).save(route);  // Si save no devuelve nada, lo simulamos con doNothing()

        // Act
        createRouteUseCase.execute(route);

        // Assert
        verify(repository, times(1)).save(route);  // Verifica que save fue llamado una vez
        System.out.println("Test 'execute_ValidRoute_CreatesRoute' passed successfully.");
    }

    @Test
    void execute_RouteIsNull_ThrowsIllegalArgumentException() {
        // Arrange
        doThrow(new IllegalArgumentException("No value present"))
                .when(repository).save(null);  // Simulamos que se lanza una excepción al pasar null

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createRouteUseCase.execute(null)  // Intentamos pasar una ruta nula
        );

        assertEquals("No value present", exception.getMessage());  // Verifica que el mensaje de la excepción sea el correcto
        verify(repository, never()).save(any(Route.class));  // No debe llamar a save si la ruta es nula
        System.out.println("Test 'execute_RouteIsNull_ThrowsIllegalArgumentException' passed successfully.");
    }
    @Test
    void execute_RouteWithNullFields_ThrowsIllegalArgumentException() {
        // Arrange
        Route invalidRoute = mock(Route.class);  // Creamos un mock de la ruta inválida
        when(invalidRoute.getStartLocation()).thenReturn(null);  // Simulamos que el campo startLocation es null
        when(invalidRoute.getEndLocation()).thenReturn(null);    // Simulamos que el campo endLocation es null
        when(invalidRoute.getPrice()).thenReturn(Price.create(10.0));  // Creamos un precio válido

        // Simulamos que el repositorio lanza una excepción cuando se pasa una ruta inválida
        doThrow(new IllegalArgumentException("Ruta con campos nulos"))
                .when(repository).save(invalidRoute);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createRouteUseCase.execute(invalidRoute)  // Intentamos ejecutar con la ruta inválida
        );

        assertEquals("Ruta con campos nulos", exception.getMessage());  // Verificamos que se lanzó la excepción con el mensaje correcto
        verify(repository, times(1)).save(invalidRoute);  // Verificamos que save fue llamado una vez
        System.out.println("Test 'execute_RouteWithNullFields_ThrowsIllegalArgumentException' passed successfully.");
    }


    @Test
    void execute_RouteWithValidData_CreatesRouteSuccessfully() {
        // Arrange
        Price price = Price.create(100.0);  // Creamos un precio válido usando Price.create()
        Route validRoute = mock(Route.class);  // Creamos un mock de Route (sin instanciarlo directamente)

        // Configuramos el mock para devolver el precio correcto cuando se le pida
        when(validRoute.getStartLocation()).thenReturn("Start");
        when(validRoute.getEndLocation()).thenReturn("End");
        when(validRoute.getPrice()).thenReturn(price);

        // Configuramos el repositorio para que cuando se guarde la ruta, se retorne la misma ruta
        doNothing().when(repository).save(validRoute);

        // Act
        createRouteUseCase.execute(validRoute);

        // Assert
        verify(repository, times(1)).save(validRoute);  // Verificamos que save fue llamado una vez con la ruta válida
        System.out.println("Test 'execute_RouteWithValidData_CreatesRouteSuccessfully' passed successfully.");
    }
}
