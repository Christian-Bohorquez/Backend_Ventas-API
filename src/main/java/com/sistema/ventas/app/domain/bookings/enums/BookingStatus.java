package com.sistema.ventas.app.domain.bookings.enums;

import com.sistema.ventas.app.domain.shared.exceptions.BusinessRuleViolationException;

public enum BookingStatus {
    FACTURADA("Facturada"),
    CANCELADA("Cancelada"),
    ANUELADA("Anulada"),
    RESERVADA("Reservada"),
    EXPIRADA("Expirada");

    private final String value;

    BookingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BookingStatus fromValue(String value) {
        for (BookingStatus status : BookingStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new BusinessRuleViolationException("Estado no válido: " + value);
    }
}
