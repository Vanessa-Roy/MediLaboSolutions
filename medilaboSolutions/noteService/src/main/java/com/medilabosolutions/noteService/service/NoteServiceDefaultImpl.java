package com.medilabosolutions.noteService.service;

import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceDefaultImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> getNotesByPatientId(long patientId) {
        return noteRepository.findAllByPatientId(patientId);
    }

    public Note createNote(Note noteToCreate) throws Exception {
        if (noteToCreate == null) {
            throw new Exception("Note to create is null");
        } else {
            return noteRepository.save(noteToCreate);
        }
    }
}
