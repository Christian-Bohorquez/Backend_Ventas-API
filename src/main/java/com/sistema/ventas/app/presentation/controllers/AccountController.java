package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.accounts.dtos.CredentialDto;
import com.sistema.ventas.app.application.accounts.services.AccountService;
import com.sistema.ventas.app.application.agents.dtos.AgentCreateDto;
import com.sistema.ventas.app.application.agents.services.AgentService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService _service;

    public  AccountController(AccountService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI<String> CreateClient(@RequestBody CredentialDto dto) {
        return _service.Authenticate(dto);
    }

}
