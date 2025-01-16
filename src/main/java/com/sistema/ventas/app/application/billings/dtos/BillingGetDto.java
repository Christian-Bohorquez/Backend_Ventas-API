package com.sistema.ventas.app.application.billings.dtos;

import java.util.UUID;

public class BillingGetDto {
    private UUID id;
    private String customerIdentification;
    private String customerName;
    private String customerEmail;
    private UUID bookingId;
    private String paymentName;
    private double totalPrice;

    public BillingGetDto(UUID id, String identification, String customerName, String customerEmail, UUID bookingId, String paymentName, double totalPrice) {
        this.id = id;
        this.customerIdentification = identification;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookingId = bookingId;
        this.paymentName = paymentName;
        this.totalPrice = totalPrice;
    }

    public UUID getId() { return id; }
    public String getCustomerIdentification() { return customerIdentification; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public UUID getBookingId() { return bookingId; }
    public String getPaymentName() { return paymentName; }
    public double getTotalPrice() { return totalPrice; }

}
