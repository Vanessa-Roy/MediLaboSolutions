package com.medilabosolutions.noteService.controller;

import com.medilabosolutions.noteService.controller.dtos.NoteDto;
import com.medilabosolutions.noteService.controller.dtos.NoteToCreateDto;
import com.medilabosolutions.noteService.mapper.NoteMapper;
import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import com.medilabosolutions.noteService.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Management of the requests though the controller.
 */
@RestController
@RequestMapping("/notes")
public class NoteController {

    /**
     * The Note service.
     */
    @Autowired
    NoteService noteService;

    /**
     * The Note mapper.
     */
    @Autowired
    NoteMapper noteMapper;

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
    @GetMapping("/{patientId}")
    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable long patientId) {
        return ResponseEntity.ok(noteService.getNotesByPatientId(patientId).stream().map(noteMapper::fromNote).toList());
    }

    /**
     * Create note for a patient.
     *
     * @param noteToCreate the note to create
     * @param patientId    the patient id
     * @return the response entity
     */
    @PostMapping("/{patientId}")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteToCreateDto noteToCreate, @PathVariable long patientId) {
        try {
            Note noteCreate = noteService.createNote(noteMapper.toNote(noteToCreate), patientId);
            return new ResponseEntity<>(noteMapper.fromNote(noteCreate), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
