package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findAllByClientId(UUID clientId);

}
