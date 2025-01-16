package com.sistema.ventas.app.domain.routes.builders;

import com.sistema.ventas.app.domain.routes.models.Route;

import java.util.UUID;

public class RouteBuilder {
    private UUID _id;
    private Double _price;
    private String _startLocation;
    private String _endLocation;

    public RouteBuilder() { }

    public RouteBuilder withId(UUID id) {
        this._id = id;
        return this;
    }

    public RouteBuilder withPrice(Double price) {
        this._price = price;
        return this;
    }

    public RouteBuilder withStartLocation(String startLocation) {
        this._startLocation = startLocation;
        return this;
    }

    public RouteBuilder withEndLocation(String endLocation) {
        this._endLocation = endLocation;
        return this;
    }

    public Route build() {
        return Route.create(
            _id,
            _price,
            _startLocation,
            _endLocation
        );
    }
}