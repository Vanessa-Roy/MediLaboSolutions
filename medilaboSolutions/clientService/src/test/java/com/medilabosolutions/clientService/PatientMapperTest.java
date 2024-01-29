package com.medilabosolutions.clientService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatientMapperTest {

    private final PatientMapper patientMapper = new PatientMapper();

    private static String patientListJson = "";

    private static List<PatientDTO> patientList;

    private static String patientJson;

    private static PatientDTO patient;

    @BeforeAll
    public static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Resource dataPatientList = new ClassPathResource("PatientCases.json");
        patientListJson = Files.readString(dataPatientList.getFile().toPath());
        patientList = objectMapper.readValue(patientListJson, new TypeReference<List<PatientDTO>>() {
        });

        Resource dataPatient = new ClassPathResource("OneOfThePatientCases.json");
        patientJson = Files.readString(dataPatient.getFile().toPath());
        patient = objectMapper.readValue(patientJson, PatientDTO.class);

    }

    @Test
    public void toListPatientWithAJsonShouldReturnPatientListTest() throws JsonProcessingException {
        List<PatientDTO> result = patientMapper.toListPatient(patientListJson);
        assertNotNull(result);
        assertEquals(patientList, result);
    }

    @Test
    public void toPatientWithAJsonShouldReturnPatientTest() throws JsonProcessingException {
        PatientDTO result = patientMapper.toPatient(patientJson);
        assertNotNull(result);
        assertEquals(patient, result);
    }

    @Test
    public void fromPatientWithAPatientShouldReturnJsonTest() throws JsonProcessingException {
        String result = patientMapper.fromPatient(patient);
        assertNotNull(result);
        assertEquals(patientJson, result);
    }
    @Test
    public void toListPatientWithAnEmptyJsonShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.toListPatient(""));
    }

    @Test
    public void toPatientWithAnEmptyJsonShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.toPatient(""));
    }

    @Test
    public void fromPatientWithANullPatientShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.fromPatient(null));
    }
}
