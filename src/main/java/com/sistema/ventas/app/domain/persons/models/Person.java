package com.sistema.ventas.app.domain.persons.models;

import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;

import java.util.UUID;

public class Person {
    protected UUID id;
    protected Identification identification;
    protected Name firstName;
    protected Name lastName;

    protected Person(UUID id, Identification identification, Name firstName, Name lastName)
    {
        this.id = id;
        this.identification = identification;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }
    public Identification getIdentification() {
        return identification;
    }
    public Name getFirstName() {
        return firstName;
    }
    public Name getLastName() {
        return lastName;
    }

    public void UpdateFirstName(String firstName)
    {
        this.firstName = Name.create(firstName, "nombres");
    }

    public void UpdateLastName(String lastName)
    {
        this.lastName = Name.create(lastName, "apellidos");
    }

}
