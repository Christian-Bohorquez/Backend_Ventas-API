package com.sistema.ventas.app.application.payments.dtos;

import java.util.UUID;

public class PaymentGetDto {
    private UUID id;
    private String name;

    public PaymentGetDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
}
