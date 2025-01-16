package com.sistema.ventas.app.application.billings.services;

import com.sistema.ventas.app.application.billings.dtos.BillingCreateDto;
import com.sistema.ventas.app.application.billings.dtos.BillingGetDto;
import com.sistema.ventas.app.application.billings.mappers.BillingMapperApp;
import com.sistema.ventas.app.domain.billings.ports.in.ICreateBillingUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IDeleteBillingByIdUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IGetAllBillingsUseCase;
import com.sistema.ventas.app.domain.billings.ports.in.IGetBillingByBookingIdUseCase;
import com.sistema.ventas.app.domain.bookings.ports.in.*;
import com.sistema.ventas.app.domain.payments.ports.in.IGetPaymentByIdUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillingService {

    private final BillingMapperApp _mapper;
    private final ICreateBillingUseCase _createBillingUseCase;
    private final IDeleteBillingByIdUseCase _deleteBillingByIdUseCase;
    private final IGetAllBillingsUseCase _getAllBillingsUseCase;
    private final IGetBillingByBookingIdUseCase _getBillingByBookingIdUseCase;
    private final IGetBookingByIdUseCase _getBookingByIdUseCase;
    private final IGetPaymentByIdUseCase _getPaymentByIdUseCase;

    public BillingService(
            BillingMapperApp mapper,
            ICreateBillingUseCase createBillingUseCase,
            IDeleteBillingByIdUseCase deleteBillingByIdUseCase,
            IGetAllBillingsUseCase getAllBillingsUseCase,
            IGetBillingByBookingIdUseCase getBillingByBookingIdUseCase,
            IGetBookingByIdUseCase getBookingByIdUseCase,
            IGetPaymentByIdUseCase getPaymentByIdUseCase )
    {
        this._mapper = mapper;
        this._createBillingUseCase = createBillingUseCase;
        this._deleteBillingByIdUseCase = deleteBillingByIdUseCase;
        this._getAllBillingsUseCase = getAllBillingsUseCase;
        this._getBillingByBookingIdUseCase = getBillingByBookingIdUseCase;
        this._getBookingByIdUseCase = getBookingByIdUseCase;
        this._getPaymentByIdUseCase = getPaymentByIdUseCase;
    }

    public ResponseAPI<String> createBilling(BillingCreateDto dto) {
        var booking = _getBookingByIdUseCase.execute(dto.getBookingId());
        var billing = _mapper.toBilling(dto, booking);
        _createBillingUseCase.execute(billing);
        return new ResponseAPI(201, "Factura generada con éxito");
    }

    public ResponseAPI deleteBilling(UUID id) {
        _deleteBillingByIdUseCase.execute(id);
        return new ResponseAPI(200, "Factura cancelada con éxito");
    }

    public ResponseAPI<BillingGetDto> getBillingByBookingId(UUID bookingId) {
        var billingDomain = _getBillingByBookingIdUseCase.execute(bookingId);
        var paymentDomain = _getPaymentByIdUseCase.execute(billingDomain.getPaymentId());
        var billingDTO = _mapper.toDtoGet(billingDomain, paymentDomain);
        return new ResponseAPI<>(200, "Información de la factura", billingDTO);
    }

    public ResponseAPI<List<BillingGetDto>> getAllBillings() {
        var billingsDomain = _getAllBillingsUseCase.execute();

        var billingsDto = billingsDomain.stream()
                .map(billing -> {
                    var paymentDomain = _getPaymentByIdUseCase.execute(billing.getPaymentId());
                    return _mapper.toDtoGet(billing, paymentDomain);
                })
                .collect(Collectors.toList());

        String message = billingsDto.isEmpty() ?
                "No se encontraron facturas registradas" :
                "Lista de facturas";

        return new ResponseAPI<>(200, message, billingsDto);
    }

}
