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
     * @return the patients
     */
    List<Patient> getPatients();

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    Patient getPatientById(Long id);

    /**
     * Update patient.
     *
     * @param patient the patient
     * @param id      the id
     * @throws Exception the exception
     */
    void updatePatient(Patient patient, Long id) throws Exception;
}