package com.medilabosolutions.patientService.service;

import com.medilabosolutions.patientService.model.Patient;

import java.util.List;

/**
 * The interface Patient service.
 */
public interface PatientService {

    /**
     * Gets patients.
     *
     * @param userId the user id
     * @return the patients
     */
    List<Patient> getPatients(String userId);

    /**
     * Gets patient by id.
     *
     * @param userId the user id
     * @param id the id
     * @return the patient by id
     */
    Patient getPatientById(String userId, Long id) throws Exception;

    /**
     * Update patient.
     *
     * @param patient the patient
     * @param id      the id
     * @throws Exception the exception
     */
    void updatePatient(Patient patient, Long id) throws Exception;
}