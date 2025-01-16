package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.infrastructure.data.entities.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperInfra {

    public RoleEntity toCreateEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        return  entity;
    }

    public Role toDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

}
