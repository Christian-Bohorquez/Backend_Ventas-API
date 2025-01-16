package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.out.IRoleRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.RoleEntity;
import com.sistema.ventas.app.infrastructure.data.mappers.RoleMapperInfra;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaRoleRepositoryAdapter implements IRoleRepositoryPort {

    private final JpaRoleRepository _repository;
    private final RoleMapperInfra _mapper;

    public JpaRoleRepositoryAdapter(
            JpaRoleRepository repository,
            RoleMapperInfra mapper )
    {
        this._repository = repository;
        this._mapper = mapper;
    }

    @Override
    public void save(Role role) {
        RoleEntity entity = _mapper.toCreateEntity(role);
        _repository.save(entity);
    }

    @Override
    public Optional<Role> getByName(String name) {
        return _repository.findByNameAndIsActiveTrue(name)
            .map(_mapper::toDomain);
    }
}
