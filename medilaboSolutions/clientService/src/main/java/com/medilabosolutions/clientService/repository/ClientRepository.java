package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository {

    List<PatientDTO> getPatients() throws Exception;

    PatientDTO getPatientById(Long id) throws Exception;

    void updatePatient(PatientDTO patient) throws Exception;
}