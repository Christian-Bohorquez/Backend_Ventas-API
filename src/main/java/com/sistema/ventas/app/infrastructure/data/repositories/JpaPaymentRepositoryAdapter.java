package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.payments.models.Payment;
import com.sistema.ventas.app.domain.payments.ports.out.IPaymentRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.PaymentEntity;
import com.sistema.ventas.app.infrastructure.data.mappers.PaymentMapperInfra;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaPaymentRepositoryAdapter implements IPaymentRepositoryPort {

    private final JpaPaymentRepository _repository;
    private final PaymentMapperInfra _mapper;

    public JpaPaymentRepositoryAdapter(
            JpaPaymentRepository repository,
            PaymentMapperInfra mapper )
    {
        this._repository = repository;
        this._mapper = mapper;
    }

    @Override
    public void save(Payment payment) {
        PaymentEntity entity = _mapper.toCreateEntity(payment);
        _repository.save(entity);
    }

    @Override
    public Optional<Payment> getById(UUID id) {
        return _repository.findById(id)
            .filter(PaymentEntity::isActive)
            .map(_mapper::toDomain);
    }

    @Override
    public Optional<Payment> getByName(String name) {
        return _repository.findByNameAndIsActiveTrue(name)
            .map(_mapper::toDomain);
    }

    @Override
    public List<Payment> getAll() {
        return _repository.findAll().stream()
            .filter(PaymentEntity::isActive)
            .map(_mapper::toDomain)
            .collect(Collectors.toList());
    }

}
