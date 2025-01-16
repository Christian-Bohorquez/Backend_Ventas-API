package com.sistema.ventas.app.domain.clients.models;

import com.sistema.ventas.app.domain.clients.valueobjects.Email;
import com.sistema.ventas.app.domain.persons.models.Person;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;

import java.util.UUID;

public class Client extends Person {
    private Email email;

    private Client(UUID id, Identification identification, Name firstName, Name lastName, Email email) {
        super(id, identification, firstName, lastName);
        this.email = email;
    }

    public static Client create(UUID id, String identification, String firstName, String lastName, String email)
    {
        return new Client(
            id != null ? id : UUID.randomUUID(),
            Identification.create(identification),
            Name.create(firstName, "nombres"),
            Name.create(lastName, "apellidos"),
            Email.create(email)
        );
    }

    public Email getEmail() {
        return email;
    }

    public void UpdateEmail(String email)
    {
        this.email = Email.create(email);
    }

}
