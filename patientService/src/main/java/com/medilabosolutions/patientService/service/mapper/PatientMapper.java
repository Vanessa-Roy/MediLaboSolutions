package com.medilabosolutions.patientService.service.mapper;

import com.medilabosolutions.patientService.dto.PatientDTO;
import com.medilabosolutions.patientService.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public PatientDTO from(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }
}
