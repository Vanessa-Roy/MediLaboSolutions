package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteMapper {

    private final ObjectMapper mapper;

    public NoteMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<NoteDto> toListNote(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

    public String fromNoteToString(NoteDto note) throws JsonProcessingException {
        if(note == null) {
            return null;
        } else {
            return mapper.writeValueAsString(note);
        }
    }
}