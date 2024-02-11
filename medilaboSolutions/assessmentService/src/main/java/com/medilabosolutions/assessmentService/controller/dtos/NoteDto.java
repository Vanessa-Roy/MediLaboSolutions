package com.medilabosolutions.assessmentService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;

import java.time.LocalDate;


@Setter
public class NoteDto {

    private String id;
    private long patientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String content;

    public NoteDto(String id, long patientId, LocalDate date, String content) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }

    public NoteDto() {
    }

    public String getContent() {
        return content;
    }
}