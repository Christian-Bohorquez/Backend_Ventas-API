package com.sistema.ventas.app.domain.shared.valueobjects;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class IdentificationDiffblueTest {
    /**
     * Test {@link Identification#create(String)}.
     * <ul>
     *   <li>When {@code 42}.</li>
     *   <li>Then throw {@link ValueObjectValidationException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Identification#create(String)}
     */
    @Test
    @DisplayName("Test create(String); when '42'; then throw ValueObjectValidationException")
    @Tag("MaintainedByDiffblue")
    void testCreate_when42_thenThrowValueObjectValidationException() {
        // Arrange, Act and Assert
        assertThrows(ValueObjectValidationException.class, () -> Identification.create("42"));
    }

    /**
     * Test {@link Identification#create(String)}.
     * <ul>
     *   <li>When {@code El campo identificación es un número inválido.}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Identification#create(String)}
     */
    @Test
    @DisplayName("Test create(String); when 'El campo identificación es un número inválido.'")
    @Tag("MaintainedByDiffblue")
    void testCreate_whenElCampoIdentificaciNEsUnNMeroInvLido() {
        // Arrange, Act and Assert
        assertThrows(ValueObjectValidationException.class,
                () -> Identification.create("El campo identificación es un número inválido."));
    }

    /**
     * Test {@link Identification#create(String)}.
     * <ul>
     *   <li>When empty string.</li>
     *   <li>Then throw {@link RequiredFieldMissingException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Identification#create(String)}
     */
    @Test
    @DisplayName("Test create(String); when empty string; then throw RequiredFieldMissingException")
    @Tag("MaintainedByDiffblue")
    void testCreate_whenEmptyString_thenThrowRequiredFieldMissingException() {
        // Arrange, Act and Assert
        assertThrows(RequiredFieldMissingException.class, () -> Identification.create(""));
    }

    /**
     * Test {@link Identification#create(String)}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then throw {@link RequiredFieldMissingException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link Identification#create(String)}
     */
    @Test
    @DisplayName("Test create(String); when 'null'; then throw RequiredFieldMissingException")
    @Tag("MaintainedByDiffblue")
    void testCreate_whenNull_thenThrowRequiredFieldMissingException() {
        // Arrange, Act and Assert
        assertThrows(RequiredFieldMissingException.class, () -> Identification.create(null));
    }

    /**
     * Test {@link Identification#getValue()}.
     * <p>
     * Method under test: {@link Identification#getValue()}
     */
    @Test
    @DisplayName("Test getValue()")
    @Disabled("TODO: Complete this test")
    @Tag("MaintainedByDiffblue")
    void testGetValue() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Exception in arrange section.
        //   Diffblue Cover was unable to construct an instance of the class under test using
        //   com.sistema.ventas.app.domain.shared.valueobjects.Identification.getValue().
        //   The arrange section threw
        //   com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException: El campo identificación es un número inválido.
        //       at com.sistema.ventas.app.domain.shared.valueobjects.Identification.create(Identification.java:26)
        //   See https://diff.blue/R081 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        Identification identification = null;

        // Act
        String actualValue = identification.getValue();

        // Assert
        // TODO: Add assertions on result
    }
}
