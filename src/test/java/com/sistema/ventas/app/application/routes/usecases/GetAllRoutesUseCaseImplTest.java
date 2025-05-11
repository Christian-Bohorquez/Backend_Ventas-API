package com.sistema.ventas.app.application.routes.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

class GetAllRoutesUseCaseImplTest {
    @Mock
    private IRouteRepositoryPort repository;

    private GetAllRoutesUseCaseImpl getAllRoutesUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getAllRoutesUseCase = new GetAllRoutesUseCaseImpl(repository);
    }

    @Test
    void execute_ReturnsAllRoutes() {
        // Arrange
        Route route1 = mock(Route.class);
        Route route2 = mock(Route.class);
        List<Route> expectedRoutes = Arrays.asList(route1, route2);
        when(repository.getAll()).thenReturn(expectedRoutes);

        // Act
        List<Route> result = getAllRoutesUseCase.execute();

        // Assert
        assertEquals(expectedRoutes, result);
        verify(repository, times(1)).getAll();
        System.out.println("Test 'execute_ReturnsAllRoutes' passed successfully.");
    }
}