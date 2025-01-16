package com.sistema.ventas.app.application.routes.mappers;

import com.sistema.ventas.app.application.routes.dtos.RouteCreateDto;
import com.sistema.ventas.app.application.routes.dtos.RouteGetDto;
import com.sistema.ventas.app.application.routes.dtos.RouteUpdateDto;
import com.sistema.ventas.app.domain.routes.builders.RouteBuilder;
import com.sistema.ventas.app.domain.routes.models.Route;
import org.springframework.stereotype.Component;

@Component
public class RouteMapperApp {

    public Route toRoute(RouteCreateDto dto) {
        return new RouteBuilder()
            .withPrice(dto.getPrice())
            .withStartLocation(dto.getStartLocation())
            .withEndLocation(dto.getEndLocation())
            .build();
    }

    public Route toRoute(RouteUpdateDto dto, Route existingRoute) {
        existingRoute.updatePrice(dto.getPrice());
        return existingRoute;
    }

    public RouteGetDto toDtoGet(Route route) {
        return new RouteGetDto(
            route.getId(),
            route.getPrice().getValue(),
            route.getStartLocation(),
            route.getEndLocation());
    }

}
