package com.sistema.ventas.app.application.routes.dtos;

public class RouteCreateDto {
    public double price;
    public String startLocation;
    public String endLocation;

    public double getPrice() { return price; }
    public String getStartLocation() { return startLocation; }
    public String getEndLocation() { return endLocation; }
}
