package com.sistema.ventas.app.application.routes.services;

import com.sistema.ventas.app.application.routes.dtos.RouteCreateDto;
import com.sistema.ventas.app.application.routes.dtos.RouteGetDto;
import com.sistema.ventas.app.application.routes.dtos.RouteUpdateDto;
import com.sistema.ventas.app.application.routes.mappers.RouteMapperApp;
import com.sistema.ventas.app.domain.routes.ports.in.*;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteMapperApp _mapper;
    private final ICreateRouteUseCase _createRouteUseCase;
    private final IUpdateRouteUseCase _updateRouteUseCase;
    private final IDeleteRouteByIdUseCase _deleteRouteByIdUseCase;
    private final IGetAllRoutesUseCase _getAllRoutesUseCase;
    private final IGetRouteByIdUseCase _getRouteByIdUseCase;

    public RouteService(
            RouteMapperApp mapper,
            ICreateRouteUseCase createRouteUseCase,
            IUpdateRouteUseCase updateRouteUseCase,
            IDeleteRouteByIdUseCase deleteRouteByIdUseCase,
            IGetAllRoutesUseCase getAllRoutesUseCase,
            IGetRouteByIdUseCase getRouteByIdUseCase)
    {
        this._mapper = mapper;
        this._createRouteUseCase = createRouteUseCase;
        this._updateRouteUseCase = updateRouteUseCase;
        this._deleteRouteByIdUseCase = deleteRouteByIdUseCase;
        this._getAllRoutesUseCase = getAllRoutesUseCase;
        this._getRouteByIdUseCase = getRouteByIdUseCase;
    }

    public ResponseAPI createRoute(RouteCreateDto dto) {
        var client = _mapper.toRoute(dto);
        _createRouteUseCase.execute(client);
        return new ResponseAPI(201, "Ruta creada exitosamente");
    }

    public ResponseAPI updateRoute(RouteUpdateDto dto) {
        var existingRoute = _getRouteByIdUseCase.execute(dto.getId());
        var updatedRoute = _mapper.toRoute(dto, existingRoute);
        _updateRouteUseCase.execute(updatedRoute);
        return new ResponseAPI(200, "Ruta actualizada con éxito");
    }

    public ResponseAPI deleteRoute(UUID id) {
        _deleteRouteByIdUseCase.execute(id);
        return new ResponseAPI(200, "Ruta eliminada con éxito");
    }

    public ResponseAPI<RouteGetDto> getRouteById(UUID routeId) {
        var routeDomain = _getRouteByIdUseCase.execute(routeId);
        var routeDTO = _mapper.toDtoGet(routeDomain);
        return new ResponseAPI<>(200, "Información de la ruta", routeDTO);
    }

    public ResponseAPI<List<RouteGetDto>>  getAllRoutes() {
        var routesDomain = _getAllRoutesUseCase.execute();
        var routesDto = routesDomain.stream()
                .map(_mapper::toDtoGet)
                .collect(Collectors.toList());

        String message = routesDto.isEmpty() ?
                "No se encontraron rutas registradas" :
                "Lista de rutas";

        return new ResponseAPI(200, message, routesDto);
    }

}
