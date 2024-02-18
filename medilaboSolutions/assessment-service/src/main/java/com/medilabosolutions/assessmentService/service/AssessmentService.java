package com.medilabosolutions.assessmentService.service;

import com.medilabosolutions.assessmentService.dtos.NoteDto;
import com.medilabosolutions.assessmentService.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.enums.Gender;
import com.medilabosolutions.assessmentService.enums.Assessment;
import com.medilabosolutions.assessmentService.enums.Trigger;
import com.medilabosolutions.assessmentService.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

/**
 * The type Assessment service.
 */
@Service
public class AssessmentService {

    /**
     * The Assessment repository.
     */
    @Autowired
    AssessmentRepository assessmentRepository;

    /**
     * Gets assessment.
     *
     * @param patientId the patient id
     * @param userId the user id
     * @return the assessment
     * @throws Exception the exception
     */
    public Assessment getAssessment(Long patientId, String userId) throws Exception {
        List<NoteDto> noteList = getNotesByPatient(patientId);
        int numberOfTriggerByPatient = (int) getNumberOfTriggerByPatient(noteList);
        if ( numberOfTriggerByPatient == 0 ) {
            return Assessment.NONE;
        }
        PatientDTO patient = getPatientById(userId, patientId);
        int patientAge = getAgePatient(patient.getBirthdate());
        if ( isEarlyOnset(numberOfTriggerByPatient, patientAge, patient.getGender()) ) {
            return Assessment.EARLY_ONSET;
        }
        if ( isInDangerAssessment(numberOfTriggerByPatient, patientAge, patient.getGender()) ) {
            return Assessment.IN_DANGER;
        }
        if ( isBorderlineAssessment(numberOfTriggerByPatient, patientAge) ) {
            return Assessment.BORDERLINE;
        }
        return Assessment.NONE;
    }


    /**
     * Gets age patient.
     *
     * @param birthdate the birthdate
     * @return the age patient
     * @throws Exception the exception
     */
    public int getAgePatient(LocalDate birthdate) throws Exception {
        if (birthdate.isAfter(LocalDate.now())) {
            throw new Exception("the date must be into the past");
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    /**
     * Gets patient by id.
     *
     * @param userId the user id
     * @param id the id
     * @return the patient by id
     * @throws Exception the exception
     */
    public PatientDTO getPatientById(String userId, long id) throws Exception {
        try {
            return assessmentRepository.getPatientById(userId, id);
        } catch (Exception e) {
            throw new Exception("Patient not found");
        }
    }

    /**
     * Gets notes by patient.
     *
     * @param id the id
     * @return the notes by patient
     * @throws Exception the exception
     */
    public List<NoteDto> getNotesByPatient(long id) throws Exception {
        try {
            return assessmentRepository.getNotesByPatientId(id);
        } catch (Exception e) {
            throw new Exception("Note not found");
        }
    }

    /**
     * Gets number of trigger by patient.
     *
     * @param noteList the note list
     * @return the number of trigger by patient
     * @throws Exception the exception
     */
    public long getNumberOfTriggerByPatient(List<NoteDto> noteList) throws Exception {
        if (noteList == null) {
            throw new Exception("NoteList is null");
        }
        return Arrays.stream(Trigger.values()).map(Trigger::getDescription)
                .filter(trigger -> noteList.stream().anyMatch(note -> note.getContent().toUpperCase().contains(trigger)))
                .count();
    }


    /**
     * Is early onset boolean.
     *
     * @param numberOfTrigger       the number of trigger by patient
     * @param patientAge    the patient age
     * @param genderPatient the gender patient
     * @return the boolean
     */
    public boolean isEarlyOnset(int numberOfTrigger, int patientAge, Gender genderPatient) {
        if ( patientAge <= 30 && genderPatient.equals(Gender.F) && numberOfTrigger >= 7 ) {
            return true;
        }
        if ( patientAge <= 30 && genderPatient.equals(Gender.M) && numberOfTrigger >= 5 ) {
            return true;
        }
        if ( patientAge > 30 && numberOfTrigger >= 8 ) {
            return true;
        }
        return false;
    }

    /**
     * Is in danger assessment boolean.
     *
     * @param numberOfTrigger       the number of trigger by patient
     * @param patientAge    the patient age
     * @param genderPatient the gender patient
     * @return the boolean
     */
    public boolean isInDangerAssessment(int numberOfTrigger, int patientAge, Gender genderPatient) {
        if ( patientAge <= 30 && genderPatient.equals(Gender.F) && numberOfTrigger >= 4 ) {
            return true;
        }
        if ( patientAge <= 30 && genderPatient.equals(Gender.M) && numberOfTrigger >= 3 ) {
            return true;
        }
        if ( patientAge > 30 && numberOfTrigger >= 6 ) {
            return true;
        }
        return false;
    }

    /**
     * Is borderline assessment boolean.
     *
     * @param numberOfTrigger    the number of trigger by patient
     * @param patientAge the patient age
     * @return the boolean
     */
    public boolean isBorderlineAssessment(int numberOfTrigger, int patientAge) {
        return patientAge > 30 && numberOfTrigger >= 2;
    }



}
