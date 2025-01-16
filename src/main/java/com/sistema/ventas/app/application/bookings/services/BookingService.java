package com.sistema.ventas.app.application.bookings.services;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.application.bookings.mappers.BookingMapperApp;
import com.sistema.ventas.app.domain.bookings.ports.in.*;
import com.sistema.ventas.app.domain.clients.ports.in.IGetClientByIdUseCase;
import com.sistema.ventas.app.domain.routes.ports.in.IGetRouteByIdUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingMapperApp _mapper;
    private final ICreateBookingUseCase _createBookingUseCase;
    private final IUpdateBookingUseCase _updateBookingUseCase;
    private final IDeleteBookingByIdUseCase _deleteBookingByIdUseCase;
    private final IGetAllBookingsUseCase _getAllBookingsUseCase;
    private final IGetAllBookingsByClientIdentificationUseCase _getAllBookingsByClientIdentificationUseCase;
    private final IGetBookingByIdUseCase _getBookingByIdUseCase;
    private final IGetClientByIdUseCase _getClientByIdUseCase;
    private final IGetRouteByIdUseCase _getRouteByIdUseCase;

    public BookingService(
            BookingMapperApp mapper,
            ICreateBookingUseCase createBookingUseCase,
            IUpdateBookingUseCase updateBookingUseCase,
            IDeleteBookingByIdUseCase deleteBookingByIdUseCase,
            IGetAllBookingsUseCase getAllBookingsUseCase,
            IGetAllBookingsByClientIdentificationUseCase getAllBookingsByClientIdentificationUseCase,
            IGetBookingByIdUseCase getBookingByIdUseCase,
            IGetClientByIdUseCase getClientByIdUseCase,
            IGetRouteByIdUseCase getRouteByIdUseCase ) {
        this._mapper = mapper;
        this._createBookingUseCase = createBookingUseCase;
        this._updateBookingUseCase = updateBookingUseCase;
        this._deleteBookingByIdUseCase = deleteBookingByIdUseCase;
        this._getAllBookingsUseCase = getAllBookingsUseCase;
        this._getAllBookingsByClientIdentificationUseCase = getAllBookingsByClientIdentificationUseCase;
        this._getBookingByIdUseCase = getBookingByIdUseCase;
        this._getClientByIdUseCase = getClientByIdUseCase;
        this._getRouteByIdUseCase = getRouteByIdUseCase;
    }

    public ResponseAPI<String> createBooking(BookingCreateDto dto) {
        var route = _getRouteByIdUseCase.execute(dto.getRouteId());
        var booking = _mapper.toBooking(dto, route);
        _createBookingUseCase.execute(booking);
        return new ResponseAPI(201, "Reserva creada exitosamente", booking.getReferenceNumber());
    }

    public ResponseAPI updateBooking(BookingUpdateDto dto) {
        var existingBooking = _getBookingByIdUseCase.execute(dto.getId());
        var updatedBooking = _mapper.toBooking(dto, existingBooking);
        _updateBookingUseCase.execute(updatedBooking);
        return new ResponseAPI(200, "Reserva actualizada con éxito");
    }

    public ResponseAPI deleteBooking(UUID id) {
        _deleteBookingByIdUseCase.execute(id);
        return new ResponseAPI(200, "Reserva cancelada con éxito");
    }

    public ResponseAPI<BookingGetDto> getBookingById(UUID bookingId) {
        var bookingDomain = _getBookingByIdUseCase.execute(bookingId);
        var clientDomain = _getClientByIdUseCase.execute(bookingDomain.getClientId());
        var bookingDTO = _mapper.toDtoGet(bookingDomain, clientDomain);
        return new ResponseAPI<>(200, "Información de la reserva", bookingDTO);
    }

    public ResponseAPI<List<BookingGetDto>> getAllBookingsByClientIdentification(String identification) {
        var bookingsDomain = _getAllBookingsByClientIdentificationUseCase.execute(identification);

        var bookingsDto = bookingsDomain.stream()
                .map(booking -> {
                    var clientDomain = _getClientByIdUseCase.execute(booking.getClientId());
                    return _mapper.toDtoGet(booking, clientDomain);
                })
                .collect(Collectors.toList());

        String message = bookingsDto.isEmpty() ?
                "No se encontraron reservas del cliente" :
                "Lista de reservas del cliente";

        return new ResponseAPI<>(200, message, bookingsDto);
    }

    public ResponseAPI<List<BookingGetDto>> getAllBookings() {
        var bookingsDomain = _getAllBookingsUseCase.execute();

        var bookingsDto = bookingsDomain.stream()
                .map(booking -> {
                    var clientDomain = _getClientByIdUseCase.execute(booking.getClientId());
                    return _mapper.toDtoGet(booking, clientDomain);
                })
                .collect(Collectors.toList());

        String message = bookingsDto.isEmpty() ?
                "No se encontraron reservas registradas" :
                "Lista de reservas";

        return new ResponseAPI<>(200, message, bookingsDto);
    }

}
