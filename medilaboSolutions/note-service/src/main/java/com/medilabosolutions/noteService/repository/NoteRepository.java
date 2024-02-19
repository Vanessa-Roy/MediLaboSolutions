package com.medilabosolutions.noteService.repository;

import com.medilabosolutions.noteService.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Note repository.
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, Integer> {
    /**
     * Find all by patient id list.
     *
     * @param patientId the patient id
     * @return the list
     */
    List<Note> findAllByPatientId(long patientId);
}
