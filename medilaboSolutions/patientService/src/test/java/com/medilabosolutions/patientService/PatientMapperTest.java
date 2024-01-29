package com.medilabosolutions.patientService;

import com.medilabosolutions.patientService.mapper.PatientMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientMapperTest {

    private final PatientMapper patientMapper = new PatientMapper();

    @Test
    public void toPatientWithANullPatientDtoShouldReturnNullTest() {
        assertNull(patientMapper.to(null));
    }

    @Test
    public void fromPatientWithANullPatientShouldReturnNullTest() {
        assertNull(patientMapper.from(null));
    }
}
