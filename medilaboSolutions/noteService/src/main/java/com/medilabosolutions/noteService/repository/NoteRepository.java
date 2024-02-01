package com.medilabosolutions.noteService.repository;

import com.medilabosolutions.noteService.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {
    List<Note> findAllByPatientId(long patientId);
}
