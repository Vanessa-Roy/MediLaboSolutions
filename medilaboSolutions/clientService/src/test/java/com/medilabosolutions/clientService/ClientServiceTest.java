package com.medilabosolutions.clientService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.repository.ClientRepository;
import com.medilabosolutions.clientService.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    private static List<PatientDTO> patientList;

    private static PatientDTO patient;

    private static PatientDTO updatePatient;

    @BeforeAll
    public static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Resource dataPatientList = new ClassPathResource("PatientCases.json");
        String patientListJson = Files.readString(dataPatientList.getFile().toPath());
        patientList = objectMapper.readValue(patientListJson, new TypeReference<List<PatientDTO>>() {
        });

        Resource dataPatient = new ClassPathResource("OneOfThePatientCases.json");
        String patientJson = Files.readString(dataPatient.getFile().toPath());
        patient = objectMapper.readValue(patientJson, PatientDTO.class);

        Resource dataUpdatePatient = new ClassPathResource("UpdateOneOfThePatientCases.json");
        String updatePatientJson = Files.readString(dataUpdatePatient.getFile().toPath());
        updatePatient = objectMapper.readValue(updatePatientJson, PatientDTO.class);
    }

    @Test
    public void getPatientsShouldCallClientRepositoryAndReturnListOfPatientsTest() throws Exception {
        when(clientRepository.getPatients()).thenReturn(patientList);

        List<PatientDTO> result = clientService.getPatients();

        verify(clientRepository, Mockito.times(1)).getPatients();
        assertEquals(patientList, result);
    }

    @Test
    public void getPatientByIdShouldCallClientRepositoryAndReturnOnePatientTest() throws Exception {
        when(clientRepository.getPatientById(anyLong())).thenReturn(patient);

        PatientDTO result = clientService.getPatientById(anyLong());

        verify(clientRepository, Mockito.times(1)).getPatientById(anyLong());
        assertEquals(patient, result);
    }

    @Test
    public void updatePatientShouldCallClientRepositoryAndUpdateThePatientTest() throws Exception {
        clientService.updatePatient(updatePatient, anyLong());

        verify(clientRepository, Mockito.times(1)).getPatientById(anyLong());
        verify(clientRepository, Mockito.times(1)).updatePatient(updatePatient);
    }
}
