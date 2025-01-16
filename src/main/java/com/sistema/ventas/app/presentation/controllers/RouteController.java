package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.routes.dtos.RouteCreateDto;
import com.sistema.ventas.app.application.routes.dtos.RouteGetDto;
import com.sistema.ventas.app.application.routes.dtos.RouteUpdateDto;
import com.sistema.ventas.app.application.routes.services.RouteService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService _service;

    public  RouteController(RouteService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI RouteClient(@RequestBody RouteCreateDto dto) {
        return _service.createRoute(dto);
    }

    @PutMapping
    public ResponseAPI UpdateRoute(@RequestBody RouteUpdateDto dto) {
        return _service.updateRoute(dto);
    }

    @GetMapping
    public ResponseAPI<List<RouteGetDto>> GetAllRoutes() {
        return _service.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseAPI<RouteGetDto> GetRouteById(@PathVariable UUID id) {
        return _service.getRouteById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseAPI DeleteRoute(@PathVariable UUID id) {
        return _service.deleteRoute(id);
    }

}
