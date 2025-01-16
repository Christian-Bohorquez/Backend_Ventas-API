package com.sistema.ventas.app.application.agents.dtos;

import lombok.Data;

public class AgentCreateDto {
    private String identification;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public String getIdentification() { return identification; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
