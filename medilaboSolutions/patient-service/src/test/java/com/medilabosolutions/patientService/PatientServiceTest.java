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
import static org.mockito.ArgumentMatchers.*;
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
        when(patientRepository.findAllByUserId(anyString())).thenReturn(new ArrayList<>());

        patientService.getPatients(anyString());

        verify(patientRepository, Mockito.times(1)).findAllByUserId(anyString());
    }

    @Test
    void getPatientByIdShouldCallFindByIdRepositoryTest() throws Exception {
        Patient patientTest = new Patient();
        patientTest.setUserId("user");
        when(patientRepository.findByIdAndUserId(1L, "user")).thenReturn(patientTest);

        patientService.getPatientById("user", 1L);

        verify(patientRepository, Mockito.times(1)).findByIdAndUserId(1L, "user");
    }

    @Test
    void getPatientByIdWithWrongUserIdShouldNotCallSaveRepositoryTest() {
        when(patientRepository.findByIdAndUserId(1L, "wrongUser")).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> patientService.getPatientById("wrongUser", 1L));

        assertEquals("Patient not found", exception.getMessage());
        verify(patientRepository, Mockito.times(1)).findByIdAndUserId(anyLong(), anyString());
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
    void updateDifferentPatientShouldNotCallSaveRepositoryTest() {
        Patient patientTest = new Patient();
        patientTest.setId(1L);

        Exception exception = assertThrows(Exception.class, () -> patientService.updatePatient(patientTest, 2L));

        assertEquals("Patient Incorrect", exception.getMessage());
        verify(patientRepository, Mockito.never()).findById(1L);
        verify(patientRepository, Mockito.never()).save(any(Patient.class));
    }
}
