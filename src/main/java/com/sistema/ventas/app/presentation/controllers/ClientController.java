package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.clients.dtos.ClientCreateDto;
import com.sistema.ventas.app.application.clients.dtos.ClientGetDto;
import com.sistema.ventas.app.application.clients.dtos.ClientUpdateDto;
import com.sistema.ventas.app.application.clients.services.ClientService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService _service;

    public  ClientController(ClientService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI CreateClient(@RequestBody ClientCreateDto dto) {
        return _service.createClient(dto);
    }

    @PutMapping
    public ResponseAPI UpdateClient(@RequestBody ClientUpdateDto dto) {
        return _service.updateClient(dto);
    }

    @GetMapping
    public ResponseAPI<List<ClientGetDto>> GetAllClients() {
        return _service.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseAPI<ClientGetDto> GetClientById(@PathVariable UUID id) {
        return _service.getClientById(id);
    }

    @GetMapping("/by-identification/{identification}")
    public ResponseAPI<ClientGetDto> GetClientByIdentification(@PathVariable String identification) {
        return _service.getClientByIdentification(identification);
    }

    @DeleteMapping("/{id}")
    public ResponseAPI DeleteClient(@PathVariable UUID id) {
        return _service.deleteClient(id);
    }

}
