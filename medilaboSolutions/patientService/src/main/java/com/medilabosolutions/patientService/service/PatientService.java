package com.medilabosolutions.patientService.service;

import com.medilabosolutions.patientService.model.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getPatients();

    Patient getPatientById(String id);

    void updatePatient(Patient patient);
}