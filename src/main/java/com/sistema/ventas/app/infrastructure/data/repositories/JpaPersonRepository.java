package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPersonRepository extends JpaRepository<PersonEntity, UUID> {
    Optional<PersonEntity> findByIdentificationAndIsActiveTrue(String identification);
}
