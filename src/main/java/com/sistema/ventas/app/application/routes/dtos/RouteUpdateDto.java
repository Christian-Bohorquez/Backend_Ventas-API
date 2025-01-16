package com.sistema.ventas.app.application.routes.dtos;

import java.util.UUID;

public class RouteUpdateDto {
    public UUID id;
    public double price;

    public UUID getId() { return id; }
    public double getPrice() { return price; }
}
