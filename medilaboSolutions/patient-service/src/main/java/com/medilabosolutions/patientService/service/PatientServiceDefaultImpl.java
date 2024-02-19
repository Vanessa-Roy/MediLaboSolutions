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
     * @param userId the user id
     * @return the patients
     */
    @Override
    public List<Patient> getPatients(String userId) {
        return patientRepository.findAllByUserId(userId);
    };

    /**
     * Gets patient by id.
     *
     * @param userId the user id
     * @param id the id
     * @return the patient by id
     */
    @Override
    public Patient getPatientById(String userId, Long id) throws Exception {
        Patient patient = patientRepository.findByIdAndUserId(id, userId);
        if (patient != null) {
            return patient;
        } else {
            throw new Exception("Patient not found");
        }
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
        if (id.equals(patient.getId())) {
            Patient patientToUpdate = patientRepository.findById(id).orElseThrow(NullPointerException::new);
            patient.setUserId(patientToUpdate.getUserId());
            patientRepository.save(patient);
        } else {
            throw new Exception("Patient Incorrect");
        }
    }
}
