package com.medilabosolutions.patientService.mapper;

import com.medilabosolutions.patientService.controller.dtos.PatientDTO;
import com.medilabosolutions.patientService.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The type Patient mapper.
 */
@Service
public class PatientMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * To dto from a patient.
     *
     * @param patient the patient
     * @return the patient dto
     */
    public PatientDTO from(Patient patient) {
        if (patient == null) {
            return null;
        } else {
            return modelMapper.map(patient, PatientDTO.class);
        }
    }

    /**
     * To patient from a dto.
     *
     * @param patient the patient
     * @return the patient
     */
    public Patient to(PatientDTO patient) {
        if (patient == null) {
            return null;
        } else {
            return modelMapper.map(patient, Patient.class);
        }
    }
}
