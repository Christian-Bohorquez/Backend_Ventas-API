package com.sistema.ventas.app.infrastructure.data.mappers;

import com.sistema.ventas.app.domain.routes.builders.RouteBuilder;
import com.sistema.ventas.app.domain.routes.models.Route;
import com.sistema.ventas.app.infrastructure.data.entities.RouteEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RouteMapperInfra {

    public RouteEntity toCreateEntity(Route domain) {
        RouteEntity entity = new RouteEntity();
        entity.setId(domain.getId());
        entity.setPrice(domain.getPrice().getValue());
        entity.setStartLocation(domain.getStartLocation());
        entity.setEndLocation(domain.getEndLocation());
        return entity;
    }

    public RouteEntity toUpdateEntity(RouteEntity entity, Route domain) {
        entity.setPrice(domain.getPrice().getValue());
        entity.setUpdatedAt(LocalDateTime.now());
        return  entity;
    }

    public Route toDomain(RouteEntity entity) {
        return new RouteBuilder()
            .withId(entity.getId())
            .withPrice(entity.getPrice())
            .withStartLocation(entity.getStartLocation())
            .withEndLocation(entity.getEndLocation())
            .build();
    }

}
