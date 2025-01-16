package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.BillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaBillingRepository extends JpaRepository<BillingEntity, UUID> {
    //@Query("SELECT b FROM BillingEntity b WHERE b.booking.id = :bookingId")
    @Query("SELECT b FROM BillingEntity b WHERE b.booking.id = :bookingId AND b.booking.status = 'Facturada'")
    Optional<BillingEntity> findByBookingIdAndStatus(UUID bookingId);
}
