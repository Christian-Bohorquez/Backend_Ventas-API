package com.sistema.ventas.app.application.clients.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

public class ClientGetDto {
    private UUID id;
    private String identification;
    private String firstName;
    private String lastName;
    private String email;

    public ClientGetDto(UUID id, String identification, String firstName, String lastName, String email) {
        this.id = id;
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getIdentification() { return identification; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
