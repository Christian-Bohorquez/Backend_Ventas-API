package com.sistema.ventas.app.application.clients.dtos;

import lombok.Data;

import java.util.UUID;

public class ClientUpdateDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
