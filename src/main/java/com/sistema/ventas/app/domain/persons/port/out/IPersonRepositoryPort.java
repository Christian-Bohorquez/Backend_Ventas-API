package com.sistema.ventas.app.domain.persons.port.out;

public interface IPersonRepositoryPort {
    boolean existsByIdentification(String identification);
}
