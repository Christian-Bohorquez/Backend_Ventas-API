package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.billings.models.Billing;
import com.sistema.ventas.app.domain.billings.ports.out.IBillingRepositoryPort;
import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.infrastructure.data.entities.BillingEntity;
import com.sistema.ventas.app.infrastructure.data.entities.BookingEntity;
import com.sistema.ventas.app.infrastructure.data.entities.PaymentEntity;
import com.sistema.ventas.app.infrastructure.data.mappers.BillingMapperInfra;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaBillingRepositoryAdapter implements IBillingRepositoryPort {

    private final JpaBillingRepository _billingRepository;
    private final JpaBookingRepository _bookingRepository;
    private final JpaPaymentRepository _paymentRepository;
    private final BillingMapperInfra _mapper;

    public  JpaBillingRepositoryAdapter(
            JpaBillingRepository billingRepository,
            JpaBookingRepository bookingRepository,
            JpaPaymentRepository paymentRepository,
            BillingMapperInfra mapper )
    {
        this._billingRepository = billingRepository;
        this._bookingRepository = bookingRepository;
        this._paymentRepository = paymentRepository;
        this._mapper = mapper;
    }

    @Transactional
    @Override
    public void save(Billing entity) {
        BookingEntity bookingEntity = _bookingRepository.findById(entity.getBooking().getId()).get();
        PaymentEntity paymentEntity = _paymentRepository.findById(entity.getPaymentId()).get();
        BillingEntity newBilling = _mapper.toCreateEntity(entity);
        newBilling.setBooking(bookingEntity);
        newBilling.setPayment(paymentEntity);
        bookingEntity.setUpdatedAt(LocalDateTime.now());
        bookingEntity.setStatus(BookingStatus.FACTURADA.getValue());
        _billingRepository.save(newBilling);
        _bookingRepository.save(bookingEntity);
    }

    @Override
    public void delete(UUID id) {
        BillingEntity billingEntity = _billingRepository.findByBookingIdAndStatus(id).get();
        _bookingRepository.findById(billingEntity.getBooking().getId()).ifPresent(booking -> {
            booking.setActive(true);
            booking.setUpdatedAt(LocalDateTime.now());
            booking.setStatus(BookingStatus.ANUELADA.getValue());
            _bookingRepository.save(booking);
        });
    }

    @Override
    public Optional<Billing> getById(UUID id) {
        return _billingRepository.findById(id)
            .map(_mapper::toDomain);
    }

    @Override
    public List<Billing> getAll() {
        return _billingRepository.findAll().stream()
            .map(_mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Billing> getByBookingId(UUID id) {
        return _billingRepository.findByBookingIdAndStatus(id)
                .map(_mapper::toDomain);
    }
}
