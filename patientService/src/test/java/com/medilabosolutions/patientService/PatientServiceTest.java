package com.medilabosolutions.patientService;

import com.medilabosolutions.patientService.repository.PatientRepository;
import com.medilabosolutions.patientService.service.PatientServiceDefaultImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceDefaultImpl patientService;

    @Test
    void findAllShouldCallFindAllRepositoryTest() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        patientService.findAll();

        verify(patientRepository, Mockito.times(1)).findAll();
    }
}
