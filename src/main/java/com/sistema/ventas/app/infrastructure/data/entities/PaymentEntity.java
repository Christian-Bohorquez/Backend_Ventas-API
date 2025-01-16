package com.sistema.ventas.app.infrastructure.data.entities;

import com.sistema.ventas.app.infrastructure.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
