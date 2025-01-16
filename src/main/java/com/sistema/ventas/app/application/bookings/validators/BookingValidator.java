package com.sistema.ventas.app.application.bookings.validators;

import com.sistema.ventas.app.domain.bookings.enums.BookingStatus;
import com.sistema.ventas.app.domain.bookings.models.Booking;
import com.sistema.ventas.app.domain.bookings.ports.out.IBookingRepositoryPort;
import com.sistema.ventas.app.domain.clients.ports.out.IClientRepositoryPort;
import com.sistema.ventas.app.domain.routes.ports.out.IRouteRepositoryPort;
import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;
import com.sistema.ventas.app.domain.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class BookingValidator {
    private final IBookingRepositoryPort _bookingRepository;
    private final IRouteRepositoryPort _routeRepository;
    private final IClientRepositoryPort _clientRepository;

    public BookingValidator(
            IBookingRepositoryPort bookingRepository,
            IRouteRepositoryPort routeRepository,
            IClientRepositoryPort clientRepository)
    {
        this._bookingRepository = bookingRepository;
        this._routeRepository = routeRepository;
        this._clientRepository = clientRepository;
    }

    public void validateCreate(Booking booking) {

        if (booking.getBookingDate().before(new Date()))
            throw new BusinessRuleViolationException("La fecha de reserva no puede ser anterior a la fecha actual.");

        _clientRepository.getById(booking.getClientId())
                .orElseThrow(() -> new NotFoundException("Cliente"));

        _routeRepository.getById(booking.getRoute().getId())
                .orElseThrow(() -> new NotFoundException("Ruta"));
    }

    public void validateGetById(UUID id) {
        _bookingRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Reserva"));
    }

    public void validateAction(String action,UUID id) {
        Booking booking = _bookingRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Reserva"));

        validateBookingStatusForAction(action, booking.getStatus());
    }

    private void validateBookingStatusForAction(String action, BookingStatus status) {
        switch (status) {
            case EXPIRADA -> throw new BusinessRuleViolationException(
                    String.format("La reserva no se puede %s, ya ha expirado.", action));
            case CANCELADA -> throw new BusinessRuleViolationException(
                    String.format("La reserva no se puede %s, ya ha sido cancelada.", action));
            case FACTURADA -> throw new BusinessRuleViolationException(
                    String.format("La reserva no se puede %s, ya ha sido facturada.", action));
            default -> { }
        }
    }
}
