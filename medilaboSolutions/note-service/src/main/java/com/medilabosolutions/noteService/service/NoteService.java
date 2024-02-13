package com.medilabosolutions.noteService.service;

import com.medilabosolutions.noteService.model.Note;

import java.util.List;

public interface NoteService {

    Note createNote(Note noteToCreate, long patientId) throws Exception;

    List<Note> getNotesByPatientId(long id);
}
