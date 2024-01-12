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
}
