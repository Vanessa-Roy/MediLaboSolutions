package com.medilabosolutions.noteService.mapper;

import com.medilabosolutions.noteService.controller.dtos.NoteDto;
import com.medilabosolutions.noteService.controller.dtos.NoteToCreateDto;
import com.medilabosolutions.noteService.model.Note;
import org.springframework.stereotype.Service;

/**
 * The type Note mapper.
 */
@Service
public class NoteMapper {

    /**
     * To note from a dto.
     *
     * @param noteDto the note dto
     * @return the note
     */
    public Note toNote(NoteToCreateDto noteDto) {
        if (noteDto == null) {
            return null;
        } else {
            return new Note(noteDto.getPatientId(), noteDto.getDate(), noteDto.getContent());
        }
    }

    /**
     * To dto from a note.
     *
     * @param note the note
     * @return the note dto
     */
    public NoteDto fromNote(Note note) {
        if (note == null) {
            return null;
        } else {
            return new NoteDto(note.getId(), note.getPatientId(), note.getDate(), note.getContent());
        }
    }
}
