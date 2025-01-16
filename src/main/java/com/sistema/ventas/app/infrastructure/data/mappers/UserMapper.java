package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.agents.models.Agent;
import com.sistema.ventas.app.infrastructure.data.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public UserEntity toCreateEntity(Agent domain) {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setUsername(domain.getUsername().getValue());
        entity.setPassword(domain.getPassword().getValue());
        return  entity;
    }
}
