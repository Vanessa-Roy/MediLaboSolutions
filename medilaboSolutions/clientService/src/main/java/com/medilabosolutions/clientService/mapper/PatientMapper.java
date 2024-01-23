package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMapper {
    
    private final ObjectMapper mapper;

    PatientMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<PatientDTO> toListPatient(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

    public PatientDTO toPatient(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

    public String fromPatient(PatientDTO patient) throws JsonProcessingException {
        if(patient == null) {
            return null;
        } else {
            return mapper.writeValueAsString(patient);
        }
    }
}
