package com.sistema.ventas.app.application.routes.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sistema.ventas.app.application.routes.dtos.RouteCreateDto;
import com.sistema.ventas.app.application.routes.dtos.RouteGetDto;
import com.sistema.ventas.app.application.routes.dtos.RouteUpdateDto;
import com.sistema.ventas.app.application.routes.mappers.RouteMapperApp;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.application.routes.usecases.*;
import com.sistema.ventas.app.application.routes.validators.RouteValidator;
import com.sistema.ventas.app.domain.routes.ports.in.*;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RouteMapperApp routeMapper;
    @Mock private ICreateRouteUseCase createRouteUseCase;
    @Mock private IUpdateRouteUseCase updateRouteUseCase;
    @Mock private IDeleteRouteByIdUseCase deleteRouteByIdUseCase;
    @Mock private IGetAllRoutesUseCase getAllRoutesUseCase;
    @Mock private IGetRouteByIdUseCase getRouteByIdUseCase;

    @InjectMocks
    private RouteService routeService;

    private UUID routeId;
    private Route route;
    private RouteCreateDto routeCreateDto;
    private RouteUpdateDto routeUpdateDto;
    private RouteGetDto routeGetDto;

    @BeforeEach
    void setup() {
        routeId = UUID.randomUUID();

        // Usa lenient para evitar que falle por stubbing innecesario
        lenient().when(getRouteByIdUseCase.execute(routeId)).thenReturn(mock(Route.class));

        routeCreateDto = new RouteCreateDto();
        routeCreateDto.price = 100.0;
        routeCreateDto.startLocation = "Start";
        routeCreateDto.endLocation = "End";

        routeUpdateDto = new RouteUpdateDto();
        routeUpdateDto.id = routeId;
        routeUpdateDto.price = 150.0;

        routeGetDto = new RouteGetDto(routeId, 100.0, "Start", "End");
    }



    @Test
    void createRoute_ValidDto_ReturnsSuccessResponse() {
        when(routeMapper.toRoute(routeCreateDto)).thenReturn(route);
        doNothing().when(createRouteUseCase).execute(route);

        ResponseAPI response = routeService.createRoute(routeCreateDto);

        assertEquals(201, response.getStatusCode());
        assertEquals("Ruta creada exitosamente", response.getMessage());
        verify(createRouteUseCase, times(1)).execute(route);

        System.out.println("PRUEBA ÉXITO: Se creó una ruta correctamente.");
    }

    @Test
    void updateRoute_ValidDto_ReturnsSuccessResponse() {
        when(getRouteByIdUseCase.execute(routeId)).thenReturn(route);
        when(routeMapper.toRoute(routeUpdateDto, route)).thenReturn(route);
        doNothing().when(updateRouteUseCase).execute(route);

        ResponseAPI response = routeService.updateRoute(routeUpdateDto);

        assertEquals(200, response.getStatusCode());
        assertEquals("Ruta actualizada con éxito", response.getMessage());
        verify(updateRouteUseCase, times(1)).execute(route);

        System.out.println("PRUEBA ÉXITO: Se actualizó una ruta correctamente.");
    }

    @Test
    void updateRoute_NonExistentRoute_ThrowsException() {
        when(getRouteByIdUseCase.execute(routeId)).thenThrow(new NotFoundException("Ruta"));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            routeService.updateRoute(routeUpdateDto);
        });

        assertEquals("No se encontró el/la Ruta.", exception.getMessage());
        verify(updateRouteUseCase, never()).execute(any(Route.class));

        System.out.println("PRUEBA ÉXITO: Se lanzó una excepción al intentar actualizar una ruta inexistente.");
    }

    @Test
    void deleteRoute_ValidId_ReturnsSuccessResponse() {
        doNothing().when(deleteRouteByIdUseCase).execute(routeId);

        ResponseAPI response = routeService.deleteRoute(routeId);

        assertEquals(200, response.getStatusCode());
        assertEquals("Ruta eliminada con éxito", response.getMessage());
        verify(deleteRouteByIdUseCase, times(1)).execute(routeId);

        System.out.println("PRUEBA ÉXITO: Se eliminó una ruta correctamente.");
    }

    @Test
    void deleteRoute_NonExistentId_ThrowsException() {
        doThrow(new NotFoundException("Ruta")).when(deleteRouteByIdUseCase).execute(routeId);

        Exception exception = assertThrows(NotFoundException.class, () -> {
            routeService.deleteRoute(routeId);
        });

        assertEquals("No se encontró el/la Ruta.", exception.getMessage());

        System.out.println("PRUEBA ÉXITO: Se lanzó una excepción al intentar eliminar una ruta inexistente.");
    }

    @Test
    void getRouteById_ExistingRoute_ReturnsRouteDto() {
        when(getRouteByIdUseCase.execute(routeId)).thenReturn(route);
        when(routeMapper.toDtoGet(route)).thenReturn(routeGetDto);

        ResponseAPI<RouteGetDto> response = routeService.getRouteById(routeId);

        assertEquals(200, response.getStatusCode());
        assertEquals("Información de la ruta", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(routeId, response.getData().getId());

        System.out.println("PRUEBA ÉXITO: Se obtuvo una ruta por ID correctamente.");
    }

    @Test
    void getRouteById_NonExistentRoute_ThrowsException() {
        when(getRouteByIdUseCase.execute(routeId)).thenThrow(new NotFoundException("Ruta"));

        Exception exception = assertThrows(NotFoundException.class, () -> {
            routeService.getRouteById(routeId);
        });

        assertEquals("No se encontró el/la Ruta.", exception.getMessage());

        System.out.println("PRUEBA ÉXITO: Se lanzó una excepción al intentar obtener una ruta inexistente.");
    }

    @Test
    void getAllRoutes_WithRoutes_ReturnsListOfRoutes() {
        List<Route> routes = Collections.singletonList(route);
        when(getAllRoutesUseCase.execute()).thenReturn(routes);
        when(routeMapper.toDtoGet(route)).thenReturn(routeGetDto);

        ResponseAPI<List<RouteGetDto>> response = routeService.getAllRoutes();

        assertEquals(200, response.getStatusCode());
        assertEquals("Lista de rutas", response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals(routeId, response.getData().get(0).getId());

        System.out.println("PRUEBA ÉXITO: Se obtuvo la lista de rutas correctamente.");
    }

    @Test
    void getAllRoutes_EmptyList_ReturnsNoRoutesMessage() {
        when(getAllRoutesUseCase.execute()).thenReturn(Collections.emptyList());

        ResponseAPI<List<RouteGetDto>> response = routeService.getAllRoutes();

        assertEquals(200, response.getStatusCode());
        assertEquals("No se encontraron rutas registradas", response.getMessage());
        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());

        System.out.println("PRUEBA ÉXITO: Se verificó que no hay rutas registradas.");
    }
}