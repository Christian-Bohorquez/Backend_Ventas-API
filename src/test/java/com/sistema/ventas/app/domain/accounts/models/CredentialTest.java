package com.sistema.ventas.app.domain.accounts.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialTest {

    @Test
    @DisplayName("Debe crear una instancia de Credential con valores válidos")
    void shouldCreateCredentialWithValidValues() {
        Credential credential = new Credential("user1", "password123");
        assertEquals("user1", credential.getUsername());
        assertEquals("password123", credential.getPassword());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el username es nulo")
    void shouldThrowExceptionIfUsernameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Credential(null, "password123"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el username está vacío")
    void shouldThrowExceptionIfUsernameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Credential("", "password123"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el password tiene menos de 6 caracteres")
    void shouldThrowExceptionIfPasswordTooShort() {
        assertThrows(IllegalArgumentException.class, () -> new Credential("user1", "12345"));
    }

    @Test
    @DisplayName("Debe permitir cambiar el username a un valor válido")
    void shouldAllowChangingUsername() {
        Credential credential = new Credential("user1", "password123");
        credential.setUsername("newuser");
        assertEquals("newuser", credential.getUsername());
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta cambiar el username a nulo o vacío")
    void shouldNotAllowEmptyOrNullUsername() {
        Credential credential = new Credential("user1", "password123");

        assertThrows(IllegalArgumentException.class, () -> credential.setUsername(null));
        assertThrows(IllegalArgumentException.class, () -> credential.setUsername(""));
    }

    @Test
    @DisplayName("Debe permitir cambiar el password a uno más largo")
    void shouldAllowSettingLongerPassword() {
        Credential credential = new Credential("user1", "password123");
        credential.setPassword("newpassword456");
        assertEquals("newpassword456", credential.getPassword());
    }

    @Test
    @DisplayName("Debe lanzar excepción si se intenta cambiar el password a uno más corto")
    void shouldNotAllowShorterPasswordChange() {
        Credential credential = new Credential("user1", "password123");
        assertThrows(IllegalArgumentException.class, () -> credential.setPassword("short"));
    }
}
