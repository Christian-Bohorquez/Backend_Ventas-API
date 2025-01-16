package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.infrastructure.data.entities.PersonEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PersonMapperInfra {

    public PersonEntity toCreateEntity(Client domain) {
        PersonEntity entity = new PersonEntity();
        entity.setId(domain.getId());
        entity.setIdentification(domain.getIdentification().getValue());
        entity.setFirstName(domain.getFirstName().getValue());
        entity.setLastName(domain.getLastName().getValue());
        return  entity;
    }

    public PersonEntity toCreateEntity(Agent domain) {
        PersonEntity entity = new PersonEntity();
        entity.setId(domain.getId());
        entity.setIdentification(domain.getIdentification().getValue());
        entity.setFirstName(domain.getFirstName().getValue());
        entity.setLastName(domain.getLastName().getValue());
        return  entity;
    }

    public PersonEntity toUpdateEntity(PersonEntity entity, Client domain) {
        entity.setFirstName(domain.getFirstName().getValue());
        entity.setLastName(domain.getLastName().getValue());
        entity.setUpdatedAt(LocalDateTime.now());
        return  entity;
    }
}
