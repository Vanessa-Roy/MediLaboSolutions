package com.medilabosolutions.patientService.mapper;

import com.medilabosolutions.patientService.controller.dtos.PatientDTO;
import com.medilabosolutions.patientService.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public PatientDTO from(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    public Patient to(PatientDTO patient) {
        return modelMapper.map(patient, Patient.class);
    }
}
