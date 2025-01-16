package com.sistema.ventas.app.domain;

import com.sistema.ventas.app.domain.shared.valueobjects.Identification;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DomainValueObjectsTest {

    @Test
    void shouldCreateValidEcuadorianIdentification() {
        String validId = "0950682401";
        Identification identification = Identification.create(validId);
        assertEquals(validId, identification.getValue());
    }

}
