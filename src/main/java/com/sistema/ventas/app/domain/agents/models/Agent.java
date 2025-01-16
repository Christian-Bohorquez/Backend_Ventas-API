package com.sistema.ventas.app.domain.agents.models;

import com.sistema.ventas.app.domain.persons.models.Person;
import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import com.sistema.ventas.app.domain.shared.valueobjects.Name;
import com.sistema.ventas.app.domain.agents.valueobjects.Password;
import com.sistema.ventas.app.domain.agents.valueobjects.Username;

import java.util.UUID;

public class Agent extends Person {
    private Username username;
    private Password password;
    private UUID roleId;

    private Agent(UUID id, Identification identification, Name firstName, Name lastName, Username username) {
        super(id, identification, firstName, lastName);
        this.username = username;
    }

    public static Agent create(UUID id, String identification, String firstName, String lastName, String username)
    {
        String fullName = firstName + " " + lastName;

        return new Agent(
                id != null ? id : UUID.randomUUID(),
                Identification.create(identification),
                Name.create(firstName, "nombres"),
                Name.create(lastName, "apellidos"),
                Username.create(username, fullName)
        );
    }

    public Username getUsername() {
        return username;
    }
    public Password getPassword() {
        return password;
    }
    public UUID getRoleId() {
        return roleId;
    }

    public void AssignPassword(String password)
    {
        this.password = Password.create(password);
    }

    public void AssignRole(UUID roleId)
    {
        if (roleId == null) throw new RequiredFieldMissingException("rol");
        this.roleId = roleId;
    }
}
