package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.clients.builders.ClientBuilder;
import com.sistema.ventas.app.domain.clients.models.Client;
import com.sistema.ventas.app.infrastructure.data.entities.ClientEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ClientMapperInfra {

    public ClientEntity toCreateEntity(Client domain) {
        ClientEntity entity = new ClientEntity();
        entity.setId(UUID.randomUUID());
        entity.setEmail(domain.getEmail().getValue());
        return entity;
    }

    public ClientEntity toUpdateEntity(ClientEntity entity, Client domain) {
        entity.setEmail(domain.getEmail().getValue());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }

    public Client toDomain(ClientEntity entity) {
        return new ClientBuilder()
            .withId(entity.getPerson().getId())
            .withIdentification(entity.getPerson().getIdentification())
            .withFirstName(entity.getPerson().getFirstName())
            .withLastName(entity.getPerson().getLastName())
            .withEmail(entity.getEmail())
            .build();
    }

}
