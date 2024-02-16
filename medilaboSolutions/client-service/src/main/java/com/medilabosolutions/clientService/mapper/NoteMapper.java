package com.medilabosolutions.clientService.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Note mapper.
 */
@Service
public class NoteMapper {

    private final ObjectMapper mapper;

    /**
     * Instantiates a new Note mapper.
     */
    public NoteMapper() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * To note list from a bodyResponse.
     *
     * @param bodyResponse the body response
     * @return the list
     * @throws JsonProcessingException the json processing exception
     */
    public List<NoteDto> toListNote(String bodyResponse) throws JsonProcessingException {
        if(bodyResponse.isEmpty()) {
            return null;
        } else {
            return mapper.readValue(bodyResponse, new TypeReference<>() {});
        }
    }

    /**
     * From note to a bodyResponse.
     *
     * @param note the note
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public String fromNoteToString(NoteDto note) throws JsonProcessingException {
        if(note == null) {
            return null;
        } else {
            return mapper.writeValueAsString(note);
        }
    }
}
