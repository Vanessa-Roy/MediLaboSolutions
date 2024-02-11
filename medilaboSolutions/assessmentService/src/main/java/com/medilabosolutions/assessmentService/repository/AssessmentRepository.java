package com.medilabosolutions.assessmentService.repository;

import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository {

    PatientDTO getPatientById(Long id) throws Exception;

    List<NoteDto> getNotesByPatientId(long patientId) throws Exception;

}
