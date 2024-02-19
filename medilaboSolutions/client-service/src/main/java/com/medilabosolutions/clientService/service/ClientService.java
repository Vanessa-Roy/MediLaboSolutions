package com.medilabosolutions.clientService.service;

import com.medilabosolutions.clientService.dtos.NoteDto;
import com.medilabosolutions.clientService.dtos.PatientDTO;
import com.medilabosolutions.clientService.enums.Assessment;
import com.medilabosolutions.clientService.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * The type Client service.
 */
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Instantiates a new Client service.
     *
     * @param clientRepository the client repository
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Gets patients.
     *
     * @param userId the user id
     * @return the patients
     * @throws Exception the exception
     */
    public List<PatientDTO> getPatients(String userId) throws Exception {
        List<PatientDTO> patientList = clientRepository.getPatients(userId);
        patientList.sort(Comparator.comparing(PatientDTO::getId));
        return patientList;
    }

    /**
     * Gets patient by id.
     *
     * @param userId the user id
     * @param id the id
     * @return the patient by id
     * @throws Exception the exception
     */
    public PatientDTO getPatientById(String userId, Long id) throws Exception {
        return clientRepository.getPatientById(userId, id);
    }

    /**
     * Update patient.
     *
     * @param userId the user id
     * @param patient the patient
     * @param id      the id
     * @throws Exception the exception
     */
    public void updatePatient(String userId, PatientDTO patient, Long id) throws Exception {
        getPatientById(userId, id);
        clientRepository.updatePatient(patient);
    }

    /**
     * Gets notes by patient id.
     *
     * @param patientId the patient id
     * @return the notes by patient id
     * @throws Exception the exception
     */
    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        List<NoteDto> noteList = clientRepository.getNotesByPatientId(patientId);
        noteList.sort(Comparator.comparing(NoteDto::getDate).reversed());
        return noteList;
    }

    /**
     * Add note to patient.
     *
     * @param note the note
     * @throws Exception the exception
     */
    public void addNoteToPatient(NoteDto note) throws Exception {
        clientRepository.addNoteToPatient(note);
    }

    /**
     * Gets assessment.
     *
     * @param userId the user id
     * @param id the id
     * @return the assessment
     * @throws Exception the exception
     */
    public Assessment getAssessment(String userId, Long id) throws Exception {
        return clientRepository.getAssessment(userId, id);
    }
}
