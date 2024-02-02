package com.medilabosolutions.clientService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class NoteDto {
    public String id;
    public long patientId;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate date;
    public String content;

    public NoteDto() {
    }

    public LocalDate getDate() {
        return date;
    }

    public NoteDto(String id, long patientId, LocalDate date, String content) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }

    public NoteDto(long patientId, LocalDate date, String content) {
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }
}
