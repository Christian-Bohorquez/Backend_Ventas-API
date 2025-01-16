package com.sistema.ventas.app.application.roles.services;

import com.sistema.ventas.app.application.roles.dtos.RoleCreateDto;
import com.sistema.ventas.app.domain.roles.models.Role;
import com.sistema.ventas.app.domain.roles.ports.in.ICreateRoleUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final ICreateRoleUseCase _createRoleUseCase;

    public RoleService(ICreateRoleUseCase createRoleUseCase) {
        this._createRoleUseCase = createRoleUseCase;
    }

    public ResponseAPI createRole(RoleCreateDto dto) {
        _createRoleUseCase.execute(new Role(dto.getName()));
        return new ResponseAPI(201, "Rol creado exitosamente");
    }
}
