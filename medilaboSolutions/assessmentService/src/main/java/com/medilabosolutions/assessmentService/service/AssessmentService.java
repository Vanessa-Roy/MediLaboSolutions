package com.medilabosolutions.assessmentService.service;

import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.controller.dtos.enums.Gender;
import com.medilabosolutions.assessmentService.model.Assessment;
import com.medilabosolutions.assessmentService.model.Trigger;
import com.medilabosolutions.assessmentService.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

@Service
public class AssessmentService {

    @Autowired
    AssessmentRepository assessmentRepository;

    public Assessment getAssessment(Long patientId) throws Exception {
        List<NoteDto> noteList = getNotesByPatient(patientId);
        int trigger = (int) getNumberOfTriggerByPatient(noteList);
        if ( trigger == 0 ) {
            return Assessment.NONE;
        }
        PatientDTO patient = getPatientById(patientId);
        int patientAge = getAgePatient(patient.getBirthdate());
        if ( isEarlyOnset(trigger, patientAge, patient.getGender()) ) {
            return Assessment.EARLY_ONSET;
        }
        if ( isInDangerAssessment(trigger, patientAge, patient.getGender()) ) {
            return Assessment.IN_DANGER;
        }
        if ( isBorderlineAssessment(trigger, patientAge) ) {
            return Assessment.BORDERLINE;
        }
        return Assessment.NONE;
    }


    public int getAgePatient(LocalDate birthdate) throws Exception {
        if (birthdate.isAfter(LocalDate.now())) {
            throw new Exception("the date must be into the past");
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public PatientDTO getPatientById(long id) throws Exception {
        try {
            return assessmentRepository.getPatientById(id);
        } catch (Exception e) {
            throw new Exception("Patient not found");
        }
    }

    public List<NoteDto> getNotesByPatient(long id) throws Exception {
        try {
            return assessmentRepository.getNotesByPatientId(id);
        } catch (Exception e) {
            throw new Exception("Note not found");
        }
    }

    public long getNumberOfTriggerByPatient(List<NoteDto> noteList) throws Exception {
        if (noteList == null) {
            throw new Exception("NoteList is null");
        }
        return Arrays.stream(Trigger.values()).map(Trigger::getDescription)
                .filter(trigger -> noteList.stream().anyMatch(note -> note.content.toUpperCase().contains(trigger)))
                .count();
    }

    public boolean isBorderlineAssessment(int trigger, int patientAge) {
        return patientAge > 30 && trigger >= 2;
    }

    public boolean isInDangerAssessment(int trigger, int patientAge, Gender genderPatient) {
        if ( patientAge <= 30 && genderPatient.equals(Gender.F) && trigger >= 4 ) {
            return true;
        }
        if ( patientAge <= 30 && genderPatient.equals(Gender.M) && trigger >= 3 ) {
            return true;
        }
        if ( patientAge > 30 && trigger >= 6 ) {
            return true;
        }
        return false;
    }

    public boolean isEarlyOnset(int trigger, int patientAge, Gender genderPatient) {
        if ( patientAge <= 30 && genderPatient.equals(Gender.F) && trigger >= 7 ) {
            return true;
        }
        if ( patientAge <= 30 && genderPatient.equals(Gender.M) && trigger >= 5 ) {
            return true;
        }
        if ( patientAge > 30 && trigger >= 8 ) {
            return true;
        }
        return false;
    }

}
