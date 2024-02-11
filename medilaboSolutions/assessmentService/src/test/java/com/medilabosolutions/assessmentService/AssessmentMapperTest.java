package com.medilabosolutions.assessmentService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.mapper.NoteMapper;
import com.medilabosolutions.assessmentService.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentMapperTest {

    private final PatientMapper patientMapper = new PatientMapper();

    private static String patientJson;

    private static PatientDTO patient;

    @BeforeAll
    public static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Resource dataPatient = new ClassPathResource("OneOfThePatientCases.json");
        patientJson = Files.readString(dataPatient.getFile().toPath());
        patient = objectMapper.readValue(patientJson, PatientDTO.class);
    }

    @Test
    public void toPatientWithAJsonShouldReturnPatientTest() throws JsonProcessingException {
        PatientDTO result = patientMapper.fromStringToPatient(patientJson);
        assertNotNull(result);
        assertEquals(patient.getLastname(), result.getLastname());
        assertEquals(patient.getFirstname(), result.getFirstname());
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getGender(), result.getGender());
    }

    @Test
    public void toPatientWithAnEmptyJsonShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.fromStringToPatient(""));
    }

    private final NoteMapper noteMapper = new NoteMapper();

    @Test
    public void toNoteWithAnEmptyNoteDtoShouldReturnNullTest() throws JsonProcessingException {
        assertNull(noteMapper.toListNote(""));
    }

}
