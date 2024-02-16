package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * To patient list from a bodyResponse.
     *
     * @param bodyResponse the body response
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public List<PatientDTO> toListPatient(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
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

    /**
     * From patient to a bodyResponse.
     *
     * @param patient the patient
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public String fromPatientToString(PatientDTO patient) throws JsonProcessingException {
        if(patient == null) {
            return null;
        } else {
            return mapper.writeValueAsString(patient);
        }
    }
}
