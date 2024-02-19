package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabosolutions.clientService.enums.Assessment;
import org.springframework.stereotype.Service;

/**
 * The type Assessment mapper.
 */
@Service
public class AssessmentMapper {
    private final ObjectMapper mapper;

    /**
     * Instantiates a new Assessment mapper.
     */
    public AssessmentMapper() {
        mapper = new ObjectMapper();
    }

    /**
     * To assessment from a bodyResponse.
     *
     * @param bodyResponse the body response
     * @return the assessment
     * @throws JsonProcessingException the json processing exception
     */
    public Assessment fromStringToAssessment(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

}
