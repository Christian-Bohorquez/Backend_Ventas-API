package com.sistema.ventas.app.application.clients.dtos;

public class ClientCreateDto {
    private String identification;
    private String firstName;
    private String lastName;
    private String email;

    public String getIdentification() { return identification; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getLastName() { return lastName; }
}
