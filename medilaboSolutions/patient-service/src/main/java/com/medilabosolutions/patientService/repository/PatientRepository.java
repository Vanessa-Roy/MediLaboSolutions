package com.medilabosolutions.patientService.repository;

import com.medilabosolutions.patientService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Patient repository.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    /**
     * Find all patients by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Patient> findAllByUserId(String userId);

    /**
     * Find the patient by id with the right user id.
     *
     * @param id the patient id
     * @param userId the user id
     * @return the list
     */
    Patient findByIdAndUserId(Long id, String userId);
}