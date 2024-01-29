package com.medilabosolutions.noteService.controller;

import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}
