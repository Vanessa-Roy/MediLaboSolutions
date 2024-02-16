package com.medilabosolutions.patientService.repository;

import com.medilabosolutions.patientService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Patient repository.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}