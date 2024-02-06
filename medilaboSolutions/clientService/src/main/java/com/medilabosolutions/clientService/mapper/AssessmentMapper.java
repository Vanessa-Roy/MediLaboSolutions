package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import org.springframework.stereotype.Service;

@Service
public class AssessmentMapper {
    private final ObjectMapper mapper;

    public AssessmentMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Assessment fromStringToAssessment(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

}
