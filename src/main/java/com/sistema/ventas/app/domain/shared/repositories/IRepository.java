package com.sistema.ventas.app.domain.shared.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRepository<T> {

    void save(T entity);

    void delete(UUID id);

    Optional<T> getById(UUID id);

    List<T> getAll();

}