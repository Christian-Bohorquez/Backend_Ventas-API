package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JapAccountRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsernameAndIsActiveTrue(String username);
    boolean existsByUsernameAndIsActiveTrue(String username);
}
