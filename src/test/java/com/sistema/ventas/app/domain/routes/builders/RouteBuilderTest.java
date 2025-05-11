package com.sistema.ventas.app.domain.routes.builders;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RouteBuilderTest {

    @Test
    @DisplayName("Debe construir una ruta válida")
    void shouldBuildValidRoute() {
        Route route = new RouteBuilder()
                .withId(UUID.randomUUID())
                .withPrice(50.0)
                .withStartLocation("New York")
                .withEndLocation("Boston")
                .build();

        assertNotNull(route);
        assertEquals("New York", route.getStartLocation());
        assertEquals("Boston", route.getEndLocation());
        assertEquals(50.0, route.getPrice().getValue());
    }

    @Test
    @DisplayName("Debe generar un ID automáticamente si no se proporciona uno")
    void shouldGenerateIdWhenIdIsNull() {
        Route route = new RouteBuilder()
                .withId(null) // No se proporciona un ID
                .withPrice(75.0)
                .withStartLocation("Los Angeles")
                .withEndLocation("San Francisco")
                .build();

        assertNotNull(route.getId());
        assertEquals("Los Angeles", route.getStartLocation());
        assertEquals("San Francisco", route.getEndLocation());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el startLocation es nulo o vacío")
    void shouldThrowExceptionWhenStartLocationIsNull() {
        RouteBuilder builder = new RouteBuilder()
                .withPrice(100.0)
                .withStartLocation(null) // startLocation nulo
                .withEndLocation("Chicago");

        Exception exception = assertThrows(RequiredFieldMissingException.class, builder::build);
        assertEquals("El campo 'Ciudad de salida' es obligatorio", exception.getMessage()); // ✅ Mensaje corregido
    }

    @Test
    @DisplayName("Debe lanzar excepción si el endLocation es nulo o vacío")
    void shouldThrowExceptionWhenEndLocationIsNull() {
        RouteBuilder builder = new RouteBuilder()
                .withPrice(100.0)
                .withStartLocation("Dallas")
                .withEndLocation(null); // endLocation nulo

        Exception exception = assertThrows(RequiredFieldMissingException.class, builder::build);
        assertEquals("El campo 'Ciudad de llegada' es obligatorio", exception.getMessage()); // ✅ Mensaje corregido
    }

    @Test
    @DisplayName("Debe crear una ruta válida y verificar el atributo de precio")
    void shouldBuildRouteWithValidPrice() {
        Route route = new RouteBuilder()
                .withId(UUID.randomUUID())
                .withPrice(200.0)
                .withStartLocation("Miami")
                .withEndLocation("Orlando")
                .build();

        assertNotNull(route.getPrice());
        assertEquals(200.0, route.getPrice().getValue());
    }
}
