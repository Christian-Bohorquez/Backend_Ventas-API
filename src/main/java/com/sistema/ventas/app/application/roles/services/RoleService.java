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
        if (dto.getName() == null) {
            return new ResponseAPI(400, "El nombre del rol no puede ser nulo");
        }

        if (dto.getName().trim().isEmpty()) {
            return new ResponseAPI(400, "El nombre del rol no puede estar vacío");
        }

        try {
            _createRoleUseCase.execute(new Role(dto.getName()));
            return new ResponseAPI(201, "Rol creado exitosamente");
        } catch (IllegalArgumentException e) {
            return new ResponseAPI(400, e.getMessage());
        }
    }

}
