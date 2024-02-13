package com.medilabosolutions.assessmentService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.mapper.NoteMapper;
import com.medilabosolutions.assessmentService.mapper.PatientMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentMapperTest {

    private final PatientMapper patientMapper = new PatientMapper();

    private static String patientJson;

    private static PatientDTO patient;

    private static String noteJson;

    private static NoteDto note;

    @BeforeAll
    public static void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Resource dataPatient = new ClassPathResource("OneOfThePatientCases.json");
        Resource dataNote = new ClassPathResource("data.json");
        patientJson = Files.readString(dataPatient.getFile().toPath());
        patient = objectMapper.readValue(patientJson, PatientDTO.class);

        noteJson = Files.readString(dataNote.getFile().toPath());
        note = new NoteDto("note1",1, LocalDate.of(2024, 1,29), "Patient reports 'feeling very good' Weight at or below recommended weight");
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
    public void toNoteWithAJsonShouldReturnNoteTest() throws JsonProcessingException {
        List<NoteDto> result = noteMapper.toListNote(noteJson);
        assertNotNull(result);
        assertEquals(note.getContent(), result.get(0).getContent());
    }

    @Test
    public void toNoteWithAnEmptyNoteDtoShouldReturnNullTest() throws JsonProcessingException {
        assertNull(noteMapper.toListNote(""));
    }

}
