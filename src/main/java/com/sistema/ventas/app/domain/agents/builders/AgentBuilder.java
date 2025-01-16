package com.sistema.ventas.app.domain.agents.builders;

import com.sistema.ventas.app.domain.agents.models.Agent;

import java.util.UUID;

public class AgentBuilder {
    private UUID _id;
    private String _identification;
    private String _firstName;
    private String _lastName;
    private String _username;

    public AgentBuilder() { }

    public AgentBuilder withId(UUID id) {
        this._id = id;
        return this;
    }

    public AgentBuilder withIdentification(String identification) {
        this._identification = identification;
        return this;
    }

    public AgentBuilder withFirstName(String firstName) {
        this._firstName = firstName;
        return this;
    }

    public AgentBuilder withLastName(String lastName) {
        this._lastName = lastName;
        return this;
    }

    public AgentBuilder withUsername(String username) {
        this._username = username;
        return this;
    }

    public Agent build() {
        return Agent.create(
            _id,
            _identification,
            _firstName,
            _lastName,
            _username
        );
    }
}
