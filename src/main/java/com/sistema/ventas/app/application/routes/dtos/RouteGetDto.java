package com.sistema.ventas.app.application.routes.dtos;

import java.util.UUID;

public class RouteGetDto {
    public UUID id;
    public double price;
    public String startLocation;
    public String endLocation;

    public RouteGetDto(UUID id, double price, String startLocation, String endLocation) {
        this.id = id;
        this.price = price;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public UUID getId() { return id; }
    public double getPrice() { return price; }
    public String getStartLocation() { return startLocation; }
    public String getEndLocation() { return endLocation; }
}
