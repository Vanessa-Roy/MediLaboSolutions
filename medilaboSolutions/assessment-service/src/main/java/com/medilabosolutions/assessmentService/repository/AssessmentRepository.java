package com.medilabosolutions.assessmentService.repository;

import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Assessment repository.
 */
@Repository
public interface AssessmentRepository {

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     * @throws Exception the exception
     */
    PatientDTO getPatientById(Long id) throws Exception;

    /**
     * Gets notes by patient id.
     *
     * @param patientId the patient id
     * @return the notes by patient id
     * @throws Exception the exception
     */
    List<NoteDto> getNotesByPatientId(long patientId) throws Exception;

}
