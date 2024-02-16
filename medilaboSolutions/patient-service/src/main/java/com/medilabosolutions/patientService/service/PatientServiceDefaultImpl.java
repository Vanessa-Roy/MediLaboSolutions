package com.medilabosolutions.patientService.service;

import com.medilabosolutions.patientService.model.Patient;
import com.medilabosolutions.patientService.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Patient service default.
 */
@Service
public class PatientServiceDefaultImpl implements PatientService {

    /**
     * The Patient repository.
     */
    @Autowired
    PatientRepository patientRepository;

    /**
     * Gets patients.
     *
     * @return the patients
     */
    @Override
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    };

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    /**
     * Update patient.
     *
     * @param patient the patient
     * @param id      the id
     * @throws Exception the exception
     */
    @Override
    public void updatePatient(Patient patient, Long id) throws Exception {
        if (id.equals(patient.getId()) && patientRepository.findById(patient.getId()).isPresent()) {
            patientRepository.save(patient);
        } else {
            throw new Exception("Patient Incorrect");
        }
    }
}
