package com.medilabosolutions.patientService.service;

import com.medilabosolutions.patientService.controller.dtos.PatientDTO;
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
    public Patient getPatientById(String id) {
        if (patientRepository.findById(Long.valueOf(id)).isPresent()) {
            return patientRepository.findById(Long.valueOf(id)).get();
        }
        return null;
    }
}
