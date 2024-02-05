package com.medilabosolutions.noteService.mapper;

import com.medilabosolutions.noteService.controller.dtos.NoteDto;
import com.medilabosolutions.noteService.controller.dtos.NoteToCreateDto;
import com.medilabosolutions.noteService.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteMapper {

    public Note toNote(NoteToCreateDto noteDto) {
        if (noteDto == null) {
            return null;
        } else {
            return new Note(noteDto.patientId, noteDto.date, noteDto.content);
        }
    }

    public NoteDto fromNote(Note note) {
        if (note == null) {
            return null;
        } else {
            return new NoteDto(note.getId(), note.getPatientId(), note.getDate(), note.getContent());
        }
    }
}
