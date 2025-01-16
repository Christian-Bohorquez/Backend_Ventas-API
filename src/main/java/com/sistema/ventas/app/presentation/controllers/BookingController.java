package com.sistema.ventas.app.presentation.controllers;

import com.sistema.ventas.app.application.bookings.dtos.BookingCreateDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingGetDto;
import com.sistema.ventas.app.application.bookings.dtos.BookingUpdateDto;
import com.sistema.ventas.app.application.bookings.services.BookingService;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService _service;

    public BookingController(BookingService service) {
        this._service = service;
    }

    @PostMapping
    public ResponseAPI createBooking(@RequestBody BookingCreateDto dto) {
        return _service.createBooking(dto);
    }

    @PutMapping
    public ResponseAPI updateBooking(@RequestBody BookingUpdateDto dto) {
        return _service.updateBooking(dto);
    }

    @GetMapping
    public ResponseAPI<List<BookingGetDto>> getAllBookings() {
        return _service.getAllBookings();
    }

    @GetMapping("/by-client-identification/{identification}")
    public ResponseAPI<List<BookingGetDto>> getAllBookings(@PathVariable String identification) {
        return _service.getAllBookingsByClientIdentification(identification);
    }

    @GetMapping("/{id}")
    public ResponseAPI<BookingGetDto> getBookingById(@PathVariable UUID id) {
        return _service.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseAPI deleteBooking(@PathVariable UUID id) {
        return _service.deleteBooking(id);
    }

}
