package com.medilabosolutions.noteService.controller.dtos;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a dto used to create a real note.
 */
@Getter
public class NoteToCreateDto {
    private long patientId;
    private LocalDate date;
    private String content;
}
