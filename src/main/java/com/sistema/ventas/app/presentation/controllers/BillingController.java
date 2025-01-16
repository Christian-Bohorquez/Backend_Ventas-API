package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.billings.dtos.BillingCreateDto;
import com.sistema.ventas.app.application.billings.dtos.BillingGetDto;
import com.sistema.ventas.app.application.billings.services.BillingService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService _service;

    public BillingController(BillingService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI createBilling(@RequestBody BillingCreateDto dto) {
        return _service.createBilling(dto);
    }

    @GetMapping
    public ResponseAPI<List<BillingGetDto>> getAllBillings() {
        return _service.getAllBillings();
    }

    @GetMapping("/by-booking-id/{bookingId}")
    public ResponseAPI<BillingGetDto> getBillingById(@PathVariable UUID bookingId) {
        return _service.getBillingByBookingId(bookingId);
    }

    @DeleteMapping("/{id}")
    public ResponseAPI deleteBilling(@PathVariable UUID id) {
        return _service.deleteBilling(id);
    }

}
