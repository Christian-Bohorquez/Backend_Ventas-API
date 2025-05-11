package com.sistema.ventas.app.domain.routes.models;

import com.sistema.ventas.app.domain.shared.exceptions.RequiredFieldMissingException;
import com.sistema.ventas.app.domain.shared.exceptions.ValueObjectValidationException;
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

        this.validate();  // Se ejecuta después de la asignación
    }

    public static Route create(UUID id, Double price, String startLocation, String endLocation) {
        if (startLocation == null || startLocation.trim().isEmpty()) {
            throw new RequiredFieldMissingException("Ciudad de salida");
        }

        if (endLocation == null || endLocation.trim().isEmpty()) {
            throw new RequiredFieldMissingException("Ciudad de llegada");
        }

        if (startLocation.equalsIgnoreCase(endLocation)) {
            throw new IllegalArgumentException("El inicio y el destino no pueden ser iguales");
        }

        if (price == null || price <= 0) {
            throw new ValueObjectValidationException("El precio debe ser mayor que 0");
        }

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

    public void updatePrice(Double newPrice) {
        if (newPrice == null || newPrice <= 0) {
            throw new ValueObjectValidationException("El nuevo precio debe ser mayor que 0");
        }
        this.price = Price.create(newPrice);
    }
}
