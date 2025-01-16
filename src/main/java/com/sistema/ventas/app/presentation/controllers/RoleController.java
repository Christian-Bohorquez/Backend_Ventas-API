package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.roles.dtos.RoleCreateDto;
import com.sistema.ventas.app.application.roles.services.RoleService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService _service;

    public RoleController(RoleService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI CreateRole(@RequestBody RoleCreateDto dto) {
        return _service.createRole(dto);
    }

}
