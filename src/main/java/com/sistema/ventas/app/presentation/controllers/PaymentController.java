package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.payments.dtos.PaymentCreateDto;
import com.sistema.ventas.app.application.payments.dtos.PaymentGetDto;
import com.sistema.ventas.app.application.payments.services.PaymentService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService _service;

    public PaymentController(PaymentService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI CreatePayment(@RequestBody PaymentCreateDto dto) {
        return _service.createPayment(dto);
    }

    @GetMapping
    public ResponseAPI<List<PaymentGetDto>> GetAllPayments() {
        return _service.getAllPayments();
    }

}
