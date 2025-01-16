package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.infrastructure.data.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientEntity, UUID> {
    @Query("SELECT c FROM ClientEntity c WHERE c.id = :id OR c.person.id = :id")
    Optional<ClientEntity> findByClientIdOrPersonId(@Param("id") UUID personId);

    @Query("SELECT c FROM ClientEntity c JOIN c.person p LEFT JOIN UserEntity u ON c.person.id = u.person.id WHERE p.identification = :identification AND (u.role IS NULL)")
    Optional<ClientEntity> findClientByIdentificationWithoutRole(@Param("identification") String identification);

    Optional<ClientEntity> findByEmailAndIsActiveTrue(String email);
}
