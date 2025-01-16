package com.sistema.ventas.app.domain.payments.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;

import java.util.UUID;

public class Payment {
    private UUID id;
    private String name;

    public Payment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RequiredFieldMissingException("nombre");
        }
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Payment(UUID id, String name) {
        if (id == null) {
            throw new RequiredFieldMissingException("id");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new RequiredFieldMissingException("nombre");
        }
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
