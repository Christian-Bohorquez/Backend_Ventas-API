package com.sistema.ventas.app.domain.clients.builders;

import com.sistema.ventas.app.domain.clients.models.Client;

import java.util.UUID;

public class ClientBuilder {
    private UUID _id;
    private String _identification;
    private String _firstName;
    private String _lastName;
    private String _email;

    public ClientBuilder() { }

    public ClientBuilder withId(UUID id) {
        this._id = id;
        return this;
    }

    public ClientBuilder withIdentification(String identification) {
        this._identification = identification;
        return this;
    }

    public ClientBuilder withFirstName(String firstName) {
        this._firstName = firstName;
        return this;
    }

    public ClientBuilder withLastName(String lastName) {
        this._lastName = lastName;
        return this;
    }

    public ClientBuilder withEmail(String email) {
        this._email = email;
        return this;
    }

    public Client build() {
        return Client.create(
            _id,
            _identification,
            _firstName,
            _lastName,
            _email
        );
    }
}
