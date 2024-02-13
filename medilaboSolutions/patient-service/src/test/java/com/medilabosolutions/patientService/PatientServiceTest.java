package com.medilabosolutions.patientService;

import com.medilabosolutions.patientService.model.Patient;
import com.medilabosolutions.patientService.repository.PatientRepository;
import com.medilabosolutions.patientService.service.PatientServiceDefaultImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceDefaultImpl patientService;

    @Test
    void getPatientsShouldCallFindAllRepositoryTest() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        patientService.getPatients();

        verify(patientRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getPatientByIdShouldCallFindByIdRepositoryTest() {
        Patient patientTest = new Patient();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patientTest));

        patientService.getPatientById(1L);

        verify(patientRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void updatePatientShouldCallSaveRepositoryTest() throws Exception {
        Patient patientTest = new Patient();
        patientTest.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patientTest));
        when(patientRepository.save(patientTest)).thenReturn(patientTest);

        patientService.updatePatient(patientTest, 1L);

        verify(patientRepository, Mockito.times(1)).findById(1L);
        verify(patientRepository, Mockito.times(1)).save(patientTest);
    }

    @Test
    void updateIncorrectPatientShouldNotCallSaveRepositoryTest() {
        Patient patientTest = new Patient();
        patientTest.setId(1L);

        Exception exception = assertThrows(Exception.class, () -> patientService.updatePatient(patientTest, 1L));

        assertEquals("Patient Incorrect", exception.getMessage());
        verify(patientRepository, Mockito.times(1)).findById(1L);
        verify(patientRepository, Mockito.never()).save(any(Patient.class));
    }

    @Test
    void updateDifferentPatientShouldNotCallSaveRepositoryTest() {
        Patient patientTest = new Patient();
        patientTest.setId(1L);

        Exception exception = assertThrows(Exception.class, () -> patientService.updatePatient(patientTest, 2L));

        assertEquals("Patient Incorrect", exception.getMessage());
        verify(patientRepository, Mockito.never()).findById(1L);
        verify(patientRepository, Mockito.never()).save(any(Patient.class));
    }
}
