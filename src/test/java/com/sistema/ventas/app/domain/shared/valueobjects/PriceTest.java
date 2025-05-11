package com.sistema.ventas.app.domain.shared.valueobjects;

import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    @DisplayName("Debe crear un precio válido")
    void shouldCreateValidPrice() {
        Price price = Price.create(25.99);
        assertNotNull(price);
        assertEquals(25.99, price.getValue());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el precio es menor o igual a 0")
    void shouldThrowExceptionWhenPriceIsZeroOrNegative() {
        assertThrows(ValueObjectValidationException.class, () -> Price.create(0.0));
        assertThrows(ValueObjectValidationException.class, () -> Price.create(-5.0));
    }

    @Test
    @DisplayName("Debe truncar el precio a dos decimales")
    void shouldTruncatePriceToTwoDecimals() {
        Price price = Price.create(25.9999);
        assertEquals(25.99, price.getValue());

        Price priceWithMoreDecimals = Price.create(12.55555);
        assertEquals(12.55, priceWithMoreDecimals.getValue());
    }

    @Test
    @DisplayName("Debe manejar precisión para valores con muchos decimales")
    void shouldHandlePrecisionWithHighDecimalValues() {
        Price price = Price.create(100.87654321);
        assertEquals(100.87, price.getValue());
    }
}
