package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Client repository.
 */
@Repository
public interface ClientRepository {

    /**
     * Gets patients.
     *
     * @return the patients
     * @throws Exception the exception
     */
    List<PatientDTO> getPatients() throws Exception;

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     * @throws Exception the exception
     */
    PatientDTO getPatientById(Long id) throws Exception;

    /**
     * Update patient.
     *
     * @param patient the patient
     * @throws Exception the exception
     */
    void updatePatient(PatientDTO patient) throws Exception;

    /**
     * Gets notes by patient id.
     *
     * @param patientId the patient id
     * @return the notes by patient id
     * @throws Exception the exception
     */
    List<NoteDto> getNotesByPatientId(long patientId) throws Exception;

    /**
     * Add note to patient.
     *
     * @param note the note
     * @throws Exception the exception
     */
    void addNoteToPatient(NoteDto note) throws Exception;

    /**
     * Gets assessment.
     *
     * @param id the id
     * @return the assessment
     * @throws Exception the exception
     */
    Assessment getAssessment(Long id) throws Exception;
}
