package com.sistema.ventas.app.domain.routes.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    @Test
    @DisplayName("Debe crear una ruta válida")
    void shouldCreateValidRoute() {
        Route route = Route.create(
                UUID.randomUUID(),
                100.0,
                "New York",
                "Boston"
        );

        assertNotNull(route);
        assertEquals("New York", route.getStartLocation());
        assertEquals("Boston", route.getEndLocation());
        assertEquals(100.0, route.getPrice().getValue());
    }

    @Test
    @DisplayName("Debe generar un ID automáticamente si no se proporciona uno")
    void shouldGenerateIdWhenIdIsNull() {
        Route route = Route.create(
                null, // ID nulo
                75.0,
                "Los Angeles",
                "San Francisco"
        );

        assertNotNull(route.getId());
        assertEquals("Los Angeles", route.getStartLocation());
        assertEquals("San Francisco", route.getEndLocation());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el startLocation es nulo o vacío")
    void shouldThrowExceptionWhenStartLocationIsNull() {
        Exception exception1 = assertThrows(RequiredFieldMissingException.class, () ->
                Route.create(UUID.randomUUID(), 50.0, null, "Chicago"));
        assertEquals("El campo 'Ciudad de salida' es obligatorio", exception1.getMessage());

        Exception exception2 = assertThrows(RequiredFieldMissingException.class, () ->
                Route.create(UUID.randomUUID(), 50.0, "", "Chicago"));
        assertEquals("El campo 'Ciudad de salida' es obligatorio", exception2.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el endLocation es nulo o vacío")
    void shouldThrowExceptionWhenEndLocationIsNull() {
        Exception exception1 = assertThrows(RequiredFieldMissingException.class, () ->
                Route.create(UUID.randomUUID(), 50.0, "Dallas", null));
        assertEquals("El campo 'Ciudad de llegada' es obligatorio", exception1.getMessage());

        Exception exception2 = assertThrows(RequiredFieldMissingException.class, () ->
                Route.create(UUID.randomUUID(), 50.0, "Dallas", ""));
        assertEquals("El campo 'Ciudad de llegada' es obligatorio", exception2.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el precio es inválido")
    void shouldThrowExceptionWhenPriceIsInvalid() {
        Exception exception1 = assertThrows(ValueObjectValidationException.class, () ->
                Route.create(UUID.randomUUID(), -10.0, "Miami", "Orlando"));
        assertEquals("El precio debe ser mayor que 0", exception1.getMessage());

        Exception exception2 = assertThrows(ValueObjectValidationException.class, () ->
                Route.create(UUID.randomUUID(), 0.0, "Miami", "Orlando"));
        assertEquals("El precio debe ser mayor que 0", exception2.getMessage());
    }

    @Test
    @DisplayName("Debe actualizar correctamente el precio de la ruta")
    void shouldUpdatePriceCorrectly() {
        Route route = Route.create(
                UUID.randomUUID(),
                200.0,
                "Miami",
                "Orlando"
        );

        route.updatePrice(250.0); // Actualizar precio

        assertEquals(250.0, route.getPrice().getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar el precio con un valor inválido")
    void shouldThrowExceptionWhenUpdatingPriceWithInvalidValue() {
        Route route = Route.create(UUID.randomUUID(), 200.0, "Miami", "Orlando");

        ValueObjectValidationException exception1 = assertThrows(ValueObjectValidationException.class, () ->
                route.updatePrice(-50.0));
        assertEquals("El nuevo precio debe ser mayor que 0", exception1.getMessage());

        ValueObjectValidationException exception2 = assertThrows(ValueObjectValidationException.class, () ->
                route.updatePrice(0.0));
        assertEquals("El nuevo precio debe ser mayor que 0", exception2.getMessage());
    }
}
