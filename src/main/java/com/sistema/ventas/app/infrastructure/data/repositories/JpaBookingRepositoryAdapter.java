package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.*;
import com.sistema.ventas.app.infrastructure.data.mappers.BookingMapperInfra;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class JpaBookingRepositoryAdapter implements IBookingRepositoryPort {

    private final JpaBookingRepository _bookingRepository;
    private final JpaClientRepository _clientRepository;
    private final JpaRouteRepository _routeRepository;
    private final BookingMapperInfra _mapper;

    public  JpaBookingRepositoryAdapter(
            JpaBookingRepository bookingRepository,
            JpaClientRepository clientRepository,
            JpaRouteRepository routeRepository,
            BookingMapperInfra mapper )
    {
        this._bookingRepository = bookingRepository;
        this._clientRepository = clientRepository;
        this._routeRepository = routeRepository;
        this._mapper = mapper;
    }

    @Override
    public void save(Booking entity) {
        Optional<ClientEntity> existingClient = _clientRepository.findByClientIdOrPersonId(entity.getClientId());
        Optional<RouteEntity> existingRoute = _routeRepository.findById(entity.getRoute().getId());
        if (existingClient.isPresent() && existingRoute.isPresent()) {
            BookingEntity newBooking = _mapper.toCreateEntity(entity);
            newBooking.setClient(existingClient.get());
            newBooking.setRoute(existingRoute.get());
            _bookingRepository.save(newBooking);
        }
    }

    @Override
    public void delete(UUID id) {
        _bookingRepository.findById(id).ifPresent(booking -> {
            booking.setActive(false);
            booking.setDeletedAt(LocalDateTime.now());
            booking.setStatus(BookingStatus.CANCELADA.getValue());
            _bookingRepository.save(booking);
        });
    }

    @Override
    public Optional<Booking> getById(UUID id) {
        updateExpiredBookings();
        return _bookingRepository.findById(id)
            .map(_mapper::toDomain);
    }

    @Override
    public List<Booking> getAll() {
        updateExpiredBookings();
        return _bookingRepository.findAll().stream()
            .map(_mapper::toDomain)
            .collect(Collectors.toList());
    }

    private void updateExpiredBookings() {
        Date now = new Date();
        List<BookingEntity> expiredBookings = _bookingRepository.findAll().stream()
                .filter(booking -> booking.isActive())
                .filter(booking -> booking.getStatus().equalsIgnoreCase(BookingStatus.RESERVADA.getValue()))
                .filter(booking -> booking.getBookingDate().before(now))
                .collect(Collectors.toList());

        expiredBookings.forEach(booking -> {
            booking.setStatus(BookingStatus.EXPIRADA.getValue());
            booking.setUpdatedAt(LocalDateTime.now());
            _bookingRepository.save(booking);
        });
    }

    @Override
    public List<Booking> getByClientIdentification(String identification) {
        return _clientRepository.findClientByIdentificationWithoutRole(identification)
            .map(client -> _bookingRepository.findAllByClientId(client.getId()).stream()
                .map(_mapper::toDomain)
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

}
