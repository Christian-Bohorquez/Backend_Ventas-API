package com.sistema.ventas.app.application.accounts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CredentialDto {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
