package com.sistema.ventas.app.application.payments.services;

import com.sistema.ventas.app.application.payments.dtos.PaymentCreateDto;
import com.sistema.ventas.app.application.payments.dtos.PaymentGetDto;
import com.sistema.ventas.app.application.payments.mappers.PaymentMapperApp;
import com.sistema.ventas.app.domain.payments.ports.in.ICreatePaymentUseCase;
import com.sistema.ventas.app.domain.payments.ports.in.IGetAllPaymentsUseCase;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentMapperApp _mapper;
    private final ICreatePaymentUseCase _createPaymentUseCase;
    private final IGetAllPaymentsUseCase _getAllPaymentsUseCase;

    public PaymentService(
            PaymentMapperApp mapper,
            ICreatePaymentUseCase createPaymentUseCase,
            IGetAllPaymentsUseCase getAllClientsUseCase )
    {
        this._mapper = mapper;
        this._createPaymentUseCase = createPaymentUseCase;
        this._getAllPaymentsUseCase = getAllClientsUseCase;
    }

    public ResponseAPI createPayment(PaymentCreateDto dto) {
        var payment = _mapper.toPayment(dto);
        _createPaymentUseCase.execute(payment);
        return new ResponseAPI(201, "Forma de pago creada exitosamente");
    }

    public ResponseAPI<List<PaymentGetDto>>  getAllPayments() {
        var paymentsDomain = _getAllPaymentsUseCase.execute();
        var paymentsDto = paymentsDomain.stream()
                .map(_mapper::toDtoGet)
                .collect(Collectors.toList());

        String message = paymentsDto.isEmpty() ?
                "No se encontraron formas de pago registradas" :
                "Lista de formas de pago";

        return new ResponseAPI(200, message, paymentsDto);
    }

}
