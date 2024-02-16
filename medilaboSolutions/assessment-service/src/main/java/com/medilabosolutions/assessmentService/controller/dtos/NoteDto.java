package com.medilabosolutions.assessmentService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Represents a dto used to read a real note.
 */
@Setter
public class NoteDto {

    private String id;
    private long patientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String content;

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

    /**
     * Instantiates a new Note dto.
     */
    public NoteDto() {
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }
}