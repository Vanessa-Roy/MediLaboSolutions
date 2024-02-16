package com.medilabosolutions.clientService.service;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<PatientDTO> getPatients() throws Exception {
        List<PatientDTO> patientList = clientRepository.getPatients();
        patientList.sort(Comparator.comparing(PatientDTO::getId));
        return patientList;
    }

    public PatientDTO getPatientById(Long id) throws Exception {
        return clientRepository.getPatientById(id);
    }

    public void updatePatient(PatientDTO patient, Long id) throws Exception {
        getPatientById(id);
        clientRepository.updatePatient(patient);
    }

    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        List<NoteDto> noteList = clientRepository.getNotesByPatientId(patientId);
        noteList.sort(Comparator.comparing(NoteDto::getDate).reversed());
        return noteList;
    }

    public void addNoteToPatient(NoteDto note) throws Exception {
        clientRepository.addNoteToPatient(note);
    }

    public Assessment getAssessment(Long id) throws Exception {
        return clientRepository.getAssessment(id);
    }
}