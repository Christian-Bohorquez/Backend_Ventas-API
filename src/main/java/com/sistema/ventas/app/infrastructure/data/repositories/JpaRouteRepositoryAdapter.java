package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.*;
import com.sistema.ventas.app.infrastructure.data.mappers.RouteMapperInfra;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaRouteRepositoryAdapter implements IRouteRepositoryPort {

    private final JpaRouteRepository _repository;
    private final RouteMapperInfra _mapper;

    public JpaRouteRepositoryAdapter(
            JpaRouteRepository repository,
            RouteMapperInfra mapper )
    {
        this._repository = repository;
        this._mapper = mapper;
    }

    @Override
    public void save(Route entity) {
        Optional<RouteEntity> existingRoute = _repository.findById(entity.getId());
        if (!existingRoute.isPresent()) {
            RouteEntity routeEntity = _mapper.toCreateEntity(entity);
            _repository.save(routeEntity);
        } else {
            RouteEntity routeEntity = _mapper.toUpdateEntity(existingRoute.get(), entity);
            _repository.save(routeEntity);
        }
    }

    @Override
    public void delete(UUID id) {
        _repository.findById(id).ifPresent(route -> {
            route.setActive(false);
            route.setDeletedAt(LocalDateTime.now());
            _repository.save(route);
        });
    }

    @Override
    public Optional<Route> getById(UUID id) {
        return _repository.findById(id)
            .filter(RouteEntity::isActive)
            .map(_mapper::toDomain);
    }

    @Override
    public List<Route> getAll() {
        return _repository.findAll().stream()
            .filter(RouteEntity::isActive)
            .map(_mapper::toDomain)
            .collect(Collectors.toList());
    }
}
