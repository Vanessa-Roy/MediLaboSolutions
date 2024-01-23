package com.medilabosolutions.patientService.service;

import com.medilabosolutions.patientService.model.Patient;
import com.medilabosolutions.patientService.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceDefaultImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    };

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public void updatePatient(Patient patient, Long id) throws Exception {
        if (id.equals(patient.getId()) && patientRepository.findById(patient.getId()).isPresent()) {
            patientRepository.save(patient);
        } else {
            throw new Exception("Patient Incorrect");
        }
    }
}
