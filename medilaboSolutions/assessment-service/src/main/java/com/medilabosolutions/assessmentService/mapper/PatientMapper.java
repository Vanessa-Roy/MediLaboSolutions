package com.medilabosolutions.assessmentService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.assessmentService.dtos.PatientDTO;
import org.springframework.stereotype.Service;

/**
 * The type Patient mapper.
 */
@Service
public class PatientMapper {
    
    private final ObjectMapper mapper;

    /**
     * Instantiates a new Patient mapper.
     */
    public PatientMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * To patient from a bodyResponse.
     *
     * @param bodyResponse the body response
     * @return the patient dto
     * @throws JsonProcessingException the json processing exception
     */
    public PatientDTO fromStringToPatient(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }
}
