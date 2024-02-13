package com.medilabosolutions.noteService.controller.dtos;

import java.time.LocalDate;

public class NoteToCreateDto {
    public long patientId;
    public LocalDate date;
    public String content;
}
