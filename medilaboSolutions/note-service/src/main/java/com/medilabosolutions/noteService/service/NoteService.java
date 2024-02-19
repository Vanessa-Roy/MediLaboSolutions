package com.medilabosolutions.noteService.service;

import com.medilabosolutions.noteService.model.Note;

import java.util.List;

/**
 * The interface Note service.
 */
public interface NoteService {

    /**
     * Create a note.
     *
     * @param noteToCreate the note to create
     * @param patientId    the patient id
     * @return the note
     * @throws Exception the exception
     */
    Note createNote(Note noteToCreate, long patientId) throws Exception;

    /**
     * Gets notes by patient id.
     *
     * @param id the id
     * @return the notes by patient id
     */
    List<Note> getNotesByPatientId(long id);
}
