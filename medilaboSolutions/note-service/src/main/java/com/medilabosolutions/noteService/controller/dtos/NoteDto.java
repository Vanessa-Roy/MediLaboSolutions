package com.medilabosolutions.noteService.controller.dtos;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a dto used to read a real note.
 */
@Getter
public class NoteDto {
    private final String id;
    private final long patientId;
    private final LocalDate date;
    private final String content;

    /**
     * Instantiates a new Note dto.
     *
     * @param id        the id
     * @param patientId the patient id
     * @param date      the date
     * @param content   the content
     */
    public NoteDto(String id, long patientId, LocalDate date, String content) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }
}
