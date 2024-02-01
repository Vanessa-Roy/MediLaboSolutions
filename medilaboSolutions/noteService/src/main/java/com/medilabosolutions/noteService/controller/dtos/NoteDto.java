package com.medilabosolutions.noteService.controller.dtos;

import java.time.LocalDate;

public class NoteDto {
    public String id;
    public long patientId;
    public LocalDate date;
    public String content;

    public NoteDto(String id, long patientId, LocalDate date, String content) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }
}
