package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository {

    List<PatientDTO> getPatients() throws Exception;

    PatientDTO getPatientById(Long id) throws Exception;

    void updatePatient(PatientDTO patient) throws Exception;

    List<NoteDto> getNotesByPatientId(long patientId) throws Exception;

    void addNoteToPatient(NoteDto note) throws Exception;

    Assessment getAssessment(Long id) throws Exception;
}
