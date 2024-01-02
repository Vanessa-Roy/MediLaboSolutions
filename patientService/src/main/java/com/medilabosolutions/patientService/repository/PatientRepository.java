package com.medilabosolutions.patientService.repository;

import com.medilabosolutions.patientService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}