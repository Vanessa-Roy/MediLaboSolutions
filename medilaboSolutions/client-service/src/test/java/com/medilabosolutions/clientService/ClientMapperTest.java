package com.medilabosolutions.clientService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.mapper.AssessmentMapper;
import com.medilabosolutions.clientService.mapper.NoteMapper;
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

public class ClientMapperTest {

    private final PatientMapper patientMapper = new PatientMapper();

    private final NoteMapper noteMapper = new NoteMapper();

    private final AssessmentMapper assessmentMapper = new AssessmentMapper();

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
        patientList = objectMapper.readValue(patientListJson, new TypeReference<>() {
        });

        Resource dataPatient = new ClassPathResource("OneOfThePatientCases.json");
        patientJson = Files.readString(dataPatient.getFile().toPath());
        patient = objectMapper.readValue(patientJson, PatientDTO.class);

    }

    @Test
    public void toListPatientWithAJsonShouldReturnPatientListTest() throws JsonProcessingException {
        List<PatientDTO> result = patientMapper.toListPatient(patientListJson);
        assertNotNull(result);
        assertEquals(2, result.size());
        for ( int i = 0 ; i < patientList.size() ; i++ ) {
            assertEquals(patientList.get(i).getId(), result.get(i).getId());
            assertEquals(patientList.get(i).getFirstname(), result.get(i).getFirstname());
            assertEquals(patientList.get(i).getLastname(), result.get(i).getLastname());
            assertEquals(patientList.get(i).getGender(), result.get(i).getGender());
            assertEquals(patientList.get(i).getPhone(), result.get(i).getPhone());
            assertEquals(patientList.get(i).getAddress(), result.get(i).getAddress());
        }
    }

    @Test
    public void toPatientWithAJsonShouldReturnPatientTest() throws JsonProcessingException {
        PatientDTO result = patientMapper.fromStringToPatient(patientJson);
        assertNotNull(result);
        assertEquals(patient.getId(), result.getId());
        assertEquals(patient.getFirstname(), result.getFirstname());
        assertEquals(patient.getLastname(), result.getLastname());
        assertEquals(patient.getGender(), result.getGender());
        assertEquals(patient.getPhone(), result.getPhone());
        assertEquals(patient.getAddress(), result.getAddress());
    }

    @Test
    public void fromPatientWithAPatientShouldReturnJsonTest() throws JsonProcessingException {
        String result = patientMapper.fromPatientToString(patient);
        assertNotNull(result);
        assertEquals(patientJson, result);
    }
    @Test
    public void toListPatientWithAnEmptyJsonShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.toListPatient(""));
    }

    @Test
    public void toPatientWithAnEmptyJsonShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.fromStringToPatient(""));
    }

    @Test
    public void fromPatientWithANullPatientShouldReturnNullTest() throws JsonProcessingException {
        assertNull(patientMapper.fromPatientToString(null));
    }

    @Test
    public void toNoteWithAnEmptyNoteDtoShouldReturnNullTest() throws JsonProcessingException {
        assertNull(noteMapper.toListNote(""));
    }

    @Test
    public void fromNoteWithANullNoteShouldReturnNullTest() throws JsonProcessingException {
        assertNull(noteMapper.fromNoteToString(null));
    }

    @Test
    public void fromAssessmentWithANullBodyShouldReturnNullTest() throws JsonProcessingException {
        assertNull(assessmentMapper.fromStringToAssessment(""));
    }
}
