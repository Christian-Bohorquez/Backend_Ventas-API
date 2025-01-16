package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findByNameAndIsActiveTrue(String name);
}
