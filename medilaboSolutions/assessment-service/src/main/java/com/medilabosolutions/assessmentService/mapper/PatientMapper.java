package com.medilabosolutions.assessmentService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {
    
    private final ObjectMapper mapper;

    public PatientMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public PatientDTO fromStringToPatient(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }
}
