package com.medilabosolutions.clientService;

import com.medilabosolutions.clientService.dtos.PatientDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientDTOTest {
    @Test
    public void getPhoneFormatTest() {
        PatientDTO patientTest = new PatientDTO();
        patientTest.setPhone("0000000000");
        assertEquals("000-000-0000",patientTest.getPhoneFormat());
    }
}
