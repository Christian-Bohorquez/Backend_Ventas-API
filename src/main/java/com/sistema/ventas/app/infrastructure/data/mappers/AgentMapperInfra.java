package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.agents.builders.AgentBuilder;
import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.infrastructure.data.entities.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class AgentMapperInfra {

    public PersonEntity toCreateEntity(Agent domain) {
        PersonEntity entity = new PersonEntity();
        entity.setId(domain.getId());
        entity.setIdentification(domain.getIdentification().getValue());
        entity.setFirstName(domain.getFirstName().getValue());
        entity.setLastName(domain.getLastName().getValue());
        entity.getUser().setUsername(domain.getUsername().getValue());
        entity.getUser().setPassword(domain.getPassword().getValue());
        entity.getUser().getRole().setId(domain.getRoleId());
        return  entity;
    }

    public Agent toDomain(PersonEntity entity) {
        return new AgentBuilder()
            .withId(entity.getId())
            .withIdentification(entity.getIdentification())
            .withFirstName(entity.getFirstName())
            .withLastName(entity.getLastName())
            .withUsername(entity.getUser().getUsername())
            .build();
    }

}
