package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.persons.port.out.IPersonRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPersonRepositoryAdapter implements IPersonRepositoryPort {

    private final JpaPersonRepository _repository;

    public JpaPersonRepositoryAdapter(JpaPersonRepository repository) {
        this._repository = repository;
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return _repository.findByIdentificationAndIsActiveTrue(identification).isPresent();
    }
}
