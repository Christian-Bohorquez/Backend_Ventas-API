package com.sistema.ventas.app.domain.routes.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.valueobjects.Price;

import java.util.UUID;

public class Route {
    private UUID id;
    private Price price;
    private String startLocation;
    private String endLocation;

    private Route(UUID id, Price price, String startLocation, String endLocation) {
        this.id = id;
        this.price = price;
        this.startLocation = startLocation;
        this.endLocation = endLocation;

        this.validate();
    }

    public static Route create(UUID id, Double price, String startLocation, String endLocation) {
        return new Route(
            id != null ? id : UUID.randomUUID(),
            Price.create(price),
            startLocation,
            endLocation
        );
    }

    private void validate() {
        if (getStartLocation() == null || getStartLocation().isEmpty()) {
            throw new RequiredFieldMissingException("Ciudad de salida");
        }

        if (getEndLocation() == null || getEndLocation().isEmpty()) {
            throw new RequiredFieldMissingException("Ciudad de llegada");
        }
    }

    public UUID getId() { return id; }
    public Price getPrice() { return price; }
    public String getStartLocation() { return startLocation; }
    public String getEndLocation() { return endLocation; }

    public void updatePrice(Double newPrice) { this.price = Price.create(newPrice); }
}
