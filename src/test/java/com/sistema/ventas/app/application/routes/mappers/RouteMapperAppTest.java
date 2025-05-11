package com.sistema.ventas.app.application.routes.mappers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sistema.ventas.app.application.routes.dtos.RouteCreateDto;
import com.sistema.ventas.app.application.routes.dtos.RouteGetDto;
import com.sistema.ventas.app.application.routes.dtos.RouteUpdateDto;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

class RouteMapperAppTest {

    private final RouteMapperApp mapper = new RouteMapperApp();

    @Test
    void toRoute_ValidRouteCreateDto_ReturnsRoute() throws Exception {
        // GIVEN
        RouteCreateDto dto = new RouteCreateDto();

        // Usamos reflexión para establecer valores dentro del DTO
        Field priceField = RouteCreateDto.class.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(dto, 100.0); // Usar el valor directo, ya que Price se maneja a través de Price.create()

        Field startLocationField = RouteCreateDto.class.getDeclaredField("startLocation");
        startLocationField.setAccessible(true);
        startLocationField.set(dto, "StartLocation");

        Field endLocationField = RouteCreateDto.class.getDeclaredField("endLocation");
        endLocationField.setAccessible(true);
        endLocationField.set(dto, "EndLocation");

        // WHEN
        Route route = mapper.toRoute(dto);

        // THEN
        try {
            assertNotNull(route, "El objeto Route no debe ser null");
            assertEquals(100.0, route.getPrice().getValue(), "El precio no coincide");
            assertEquals("StartLocation", route.getStartLocation(), "La ubicación de inicio no coincide");
            assertEquals("EndLocation", route.getEndLocation(), "La ubicación de fin no coincide");
            System.out.println("Prueba exitosa: El mapeo de RouteCreateDto a Route fue exitoso.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void toRoute_ValidRouteUpdateDto_UpdatesRoute() throws Exception {
        // GIVEN
        RouteUpdateDto dto = new RouteUpdateDto();
        dto.price = 150.0;  // Directamente el valor en lugar de usar Price

        // Usamos un Route con los valores iniciales
        Route existingRoute = Route.create(UUID.randomUUID(), 100.0, "OldStartLocation", "OldEndLocation");

        // WHEN
        Route updatedRoute = mapper.toRoute(dto, existingRoute);

        // THEN
        try {
            assertNotNull(updatedRoute, "El objeto Route actualizado no debe ser null");
            assertEquals(150.0, updatedRoute.getPrice().getValue(), "El precio actualizado no coincide");
            System.out.println("Prueba exitosa: El mapeo de RouteUpdateDto a Route fue exitoso.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void toDtoGet_ValidRoute_ReturnsRouteGetDto() {
        // GIVEN
        UUID routeId = UUID.randomUUID();
        Route route = Route.create(routeId, 200.0, "Start", "End");

        // WHEN
        RouteGetDto dto = mapper.toDtoGet(route);

        // THEN
        try {
            assertNotNull(dto, "El objeto RouteGetDto no debe ser null");
            assertEquals(routeId, dto.getId(), "El ID no coincide");
            assertEquals(200.0, dto.getPrice(), "El precio no coincide");
            assertEquals("Start", dto.getStartLocation(), "La ubicación de inicio no coincide");
            assertEquals("End", dto.getEndLocation(), "La ubicación de fin no coincide");
            System.out.println("Prueba exitosa: El mapeo de Route a RouteGetDto fue exitoso.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba: " + e.getMessage());
            throw e;
        }
    }


    @Test
    void toRoute_NullRouteCreateDto_ThrowsException() {
        // GIVEN
        RouteCreateDto dto = null;

        // WHEN & THEN
        try {
            assertThrows(NullPointerException.class, () -> mapper.toRoute(dto),
                    "Debería lanzar una excepción si el RouteCreateDto es null");
            System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un RouteCreateDto null.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toRoute_NullRouteCreateDto_ThrowsException: " + e.getMessage());
            throw e;
        }
    }

    @Test
    void toDtoGet_NullRoute_ThrowsException() {
        // GIVEN
        Route route = null;

        // WHEN & THEN
        try {
            assertThrows(NullPointerException.class, () -> mapper.toDtoGet(route),
                    "Debería lanzar una excepción si el Route es null");
            System.out.println("Prueba exitosa: Se lanzó la excepción esperada al pasar un Route null.");
        } catch (AssertionError e) {
            System.out.println("Error en la prueba toDtoGet_NullRoute_ThrowsException: " + e.getMessage());
            throw e;
        }
    }
}
