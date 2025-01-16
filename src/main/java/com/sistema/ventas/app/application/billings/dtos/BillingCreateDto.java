package com.sistema.ventas.app.application.billings.dtos;

import java.util.UUID;

public class BillingCreateDto {
    private String customerIdentification;
    private String customerName;
    private String customerEmail;
    private UUID paymentId;
    private UUID bookingId;

    public String getCustomerIdentification() { return customerIdentification; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public UUID getPaymentId() { return paymentId; }
    public UUID getBookingId() { return bookingId; }
}
