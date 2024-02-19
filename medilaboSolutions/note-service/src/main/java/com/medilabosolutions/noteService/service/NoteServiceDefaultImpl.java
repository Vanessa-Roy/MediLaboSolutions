package com.medilabosolutions.noteService.service;

import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Note service default.
 */
@Service
public class NoteServiceDefaultImpl implements NoteService {

    /**
     * The Note repository.
     */
    @Autowired
    NoteRepository noteRepository;

    /**
     * Gets notes by patient id.
     *
     * @param patientId the patient id
     * @return the notes by patient id
     */
    public List<Note> getNotesByPatientId(long patientId) {
        return noteRepository.findAllByPatientId(patientId);
    }

    /**
     * Create a note.
     *
     * @param noteToCreate the note to create
     * @param patientId    the patient id
     * @return the note
     * @throws Exception the exception
     */
    public Note createNote(Note noteToCreate, long patientId) throws Exception {
        if (noteToCreate == null) {
            throw new Exception("Note to create is null");
        } else if (patientId != noteToCreate.getPatientId()) {
            throw new Exception("Note incorrect");
        } else {
            return noteRepository.save(noteToCreate);
        }
    }
}
