package com.sistema.ventas.app.infrastructure.data.entities;

import com.sistema.ventas.app.infrastructure.common.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "route")
public class RouteEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false, length = 255)
    private String startLocation;

    @Column(nullable = false, length = 255)
    private String endLocation;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getStartLocation() { return startLocation; }
    public void setStartLocation(String startLocation) { this.startLocation = startLocation; }
    public String getEndLocation() { return endLocation; }
    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

}
