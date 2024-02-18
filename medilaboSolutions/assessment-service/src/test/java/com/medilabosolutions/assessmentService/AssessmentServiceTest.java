package com.medilabosolutions.assessmentService;

import com.medilabosolutions.assessmentService.dtos.NoteDto;
import com.medilabosolutions.assessmentService.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.enums.Gender;
import com.medilabosolutions.assessmentService.enums.Assessment;
import com.medilabosolutions.assessmentService.enums.Trigger;
import com.medilabosolutions.assessmentService.repository.AssessmentRepository;
import com.medilabosolutions.assessmentService.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @InjectMocks
    AssessmentService assessmentService;

    @Mock
    AssessmentRepository assessmentRepository;

    PatientDTO patientTest;
    NoteDto noteTest;
    List<NoteDto> noteListTest;
    Trigger trigger;

    @BeforeEach
    public void setUpPerTest() {
        patientTest = new PatientDTO(1L, "firstnameTest", "lastnameTest", LocalDate.now().minusYears(31), Gender.F, null, null);
        noteTest = new NoteDto("note1",1L, LocalDate.now().minusMonths(1),"reaction");
        noteListTest = new ArrayList<>(List.of(noteTest));
    }

    @Test
    public void getAgePatientShouldReturnTheAge() throws Exception {
        assertEquals(31,assessmentService.getAgePatient(patientTest.getBirthdate()));
    }

    @Test
    public void getAgePatientWithIncorrectBirthdateShouldNotReturnTheAge() {

        Exception exception = assertThrows(Exception.class, () -> assessmentService.getAgePatient(LocalDate.now().plusYears(30)));

        assertEquals("the date must be into the past", exception.getMessage());
    }

    @Test
    public void getPatientByIdShouldReturnThePatient() throws Exception {
        when(assessmentRepository.getPatientById( "user", 1L)).thenReturn(patientTest);

        assertEquals(patientTest,assessmentService.getPatientById( "user", 1L));

        verify(assessmentRepository, times(1)).getPatientById( "user", 1L);
    }

    @Test
    public void getPatientIdWithIncorrectIdShouldNotReturnThePatient() throws Exception {
        when(assessmentRepository.getPatientById("user", 1L)).thenThrow(Exception.class);

        Exception exception = assertThrows(Exception.class, () -> assessmentService.getPatientById( "user", 1L));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    public void getNotesByPatientShouldReturnTheNotes() throws Exception {
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(noteListTest);

        assertEquals(noteListTest,assessmentService.getNotesByPatient(1L));
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getNotesByPatientWithIncorrectIdShouldNotReturnTheNotes() throws Exception {
        when(assessmentRepository.getNotesByPatientId(1L)).thenThrow(Exception.class);

        Exception exception = assertThrows(Exception.class, () -> assessmentService.getNotesByPatient(1L));

        assertEquals("Note not found", exception.getMessage());
    }

    @Test
    public void getNumberOfTriggerByNoteListShouldReturnTheCount() throws Exception {
        assertEquals(1,assessmentService.getNumberOfTriggerByPatient(noteListTest));
    }

    @Test
    public void getNumberOfTriggerByEmptyNoteListShouldReturnTheCount() throws Exception {
        assertEquals(0,assessmentService.getNumberOfTriggerByPatient(new ArrayList<>()));
    }

    @Test
    public void getNumberOfTriggerByIncorrectNoteListShouldReturnTheCount() {
        Exception exception = assertThrows(Exception.class, () -> assessmentService.getNumberOfTriggerByPatient(null));

        assertEquals("NoteList is null", exception.getMessage());
    }

    @Test
    public void isBorderlineAssessmentWithAgeMoreThanThirtyAnd2TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isBorderlineAssessment(2, 35));
    }

    @Test
    public void isBorderlineAssessmentWithAgeLessThanThirtyAnd2TriggerOrMoreShouldReturnFalse() {
        assertFalse(assessmentService.isBorderlineAssessment(2, 29));
    }

    @Test
    public void isBorderlineAssessmentWithAgeMoreThanThirtyAndTriggerLessThan2ShouldReturnFalse() {
        assertFalse(assessmentService.isBorderlineAssessment(1, 35));
    }

    @Test
    public void isInDangerAssessmentWithFemaleAndAgeMoreThanThirtyAnd6TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isInDangerAssessment(6, 35, Gender.F));
    }

    @Test
    public void isInDangerAssessmentWithMaleAndAgeMoreThanThirtyAndTriggerLessThan6ShouldReturnFalse() {
        assertFalse(assessmentService.isInDangerAssessment(5, 35, Gender.M));
    }

    @Test
    public void isInDangerAssessmentWithFemaleAndAgeLessThanThirtyAnd4TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isInDangerAssessment(4, 29, Gender.F));
    }

    @Test
    public void isInDangerAssessmentWithFemaleAndAgeLessThanThirtyAndTriggerLessThan4ShouldReturnFalse() {
        assertFalse(assessmentService.isInDangerAssessment(3, 29, Gender.F));
    }

    @Test
    public void isInDangerAssessmentWithMaleAndAgeLessThanThirtyAnd3TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isInDangerAssessment(3, 29, Gender.M));
    }

    @Test
    public void isInDangerAssessmentWithMaleAndAgeLessThanThirtyAndTriggerLessThan3ShouldReturnFalse() {
        assertFalse(assessmentService.isInDangerAssessment(2, 29, Gender.M));
    }

    @Test
    public void isEarlyOnsetAssessmentWithFemaleAndAgeMoreThanThirtyAnd8TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isEarlyOnset(8, 35, Gender.F));
    }

    @Test
    public void isEarlyOnsetAssessmentWithMaleAndAgeMoreThanThirtyAndTriggerLessThan8ShouldReturnFalse() {
        assertFalse(assessmentService.isEarlyOnset(7, 35, Gender.M));
    }

    @Test
    public void isEarlyOnsetAssessmentWithFemaleAndAgeLessThanThirtyAnd7TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isEarlyOnset(7, 29, Gender.F));
    }

    @Test
    public void isEarlyOnsetAssessmentWithFemaleAndAgeLessThanThirtyAndTriggerLessThan7ShouldReturnFalse() {
        assertFalse(assessmentService.isEarlyOnset(6, 29, Gender.F));
    }

    @Test
    public void isEarlyOnsetAssessmentWithMaleAndAgeLessThanThirtyAnd5TriggerOrMoreShouldReturnTrue() {
        assertTrue(assessmentService.isEarlyOnset(5, 29, Gender.M));
    }

    @Test
    public void isEarlyOnsetAssessmentWithMaleAndAgeLessThanThirtyAndTriggerLessThan5ShouldReturnFalse() {
        assertFalse(assessmentService.isEarlyOnset(4, 29, Gender.M));
    }

    @Test
    public void getAssessmentWithEmptyNoteAndCorrectPatientIdShouldReturnTheAssessmentNone() throws Exception {
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(new ArrayList<>());

        assertEquals(Assessment.NONE, assessmentService.getAssessment(1L, "user"));

        verify(assessmentRepository, never()).getPatientById("user", 1L);
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getAssessmentWithLessThan2TriggersAndCorrectPatientIdShouldReturnTheAssessmentNone() throws Exception {
        when(assessmentRepository.getPatientById("user",1L)).thenReturn(patientTest);
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(noteListTest);

        assertEquals(Assessment.NONE, assessmentService.getAssessment(1L, "user"));

        verify(assessmentRepository, times(1)).getPatientById( "user", 1L);
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getAssessmentWith8TriggersAndCorrectPatientIdShouldReturnTheAssessmentIsEarlyOnset() throws Exception {
        when(assessmentRepository.getPatientById("user",1L)).thenReturn(patientTest);
        noteTest.setContent("reaction, cholesterol, smoker, weight, height, relapse, abnormal, dizziness");
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(noteListTest);

        assertEquals(Assessment.EARLY_ONSET, assessmentService.getAssessment(1L, "user"));

        verify(assessmentRepository, times(1)).getPatientById( "user", 1L);
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getAssessmentWith4TriggersAndCorrectPatientIdShouldReturnTheAssessmentIsInDanger() throws Exception {
        when(assessmentRepository.getPatientById("user",1L)).thenReturn(patientTest);
        noteTest.setContent("reaction, cholesterol, smoker, weight, height, relapse");
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(noteListTest);

        assertEquals(Assessment.IN_DANGER, assessmentService.getAssessment(1L, "user"));

        verify(assessmentRepository, times(1)).getPatientById( "user", 1L);
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }

    @Test
    public void getAssessmentWith2TriggersAndCorrectPatientIdShouldReturnTheAssessmentBorderline() throws Exception {
        when(assessmentRepository.getPatientById( "user", 1L)).thenReturn(patientTest);
        noteTest.setContent("reaction, cholesterol");
        when(assessmentRepository.getNotesByPatientId(1L)).thenReturn(noteListTest);

        assertEquals(Assessment.BORDERLINE, assessmentService.getAssessment(1L, "user"));

        verify(assessmentRepository, times(1)).getPatientById("user", 1L);
        verify(assessmentRepository, times(1)).getNotesByPatientId(1L);
    }
}
