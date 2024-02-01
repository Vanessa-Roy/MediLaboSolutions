package com.medilabosolutions.noteService.controller;

import com.medilabosolutions.noteService.controller.dtos.NoteDto;
import com.medilabosolutions.noteService.controller.dtos.NoteToCreateDto;
import com.medilabosolutions.noteService.mapper.NoteMapper;
import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients/{patientId}")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteMapper noteMapper;

    @GetMapping("/notes")
    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable long patientId) {
        return ResponseEntity.ok(noteService.getNotesByPatientId(patientId).stream().map(noteMapper::fromNote).toList());
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteToCreateDto noteToCreate) {
        try {
            Note noteCreate = noteService.createNote(noteMapper.toNote(noteToCreate));
            return new ResponseEntity<>(noteMapper.fromNote(noteCreate), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
