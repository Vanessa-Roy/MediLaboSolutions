package com.medilabosolutions.noteService.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Represent a note
 */
@Document("notes")
@Getter
public class Note {
    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;

    /**
     * Sets patient id.
     *
     * @param patientId the patient id
     */
    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    private long patientId;
    private LocalDate date;
    private String content;

    /**
     * Instantiates a new Note.
     */
    public Note() {
    }

    /**
     * Instantiates a new Note.
     *
     * @param patientId the patient id
     * @param date      the date
     * @param content   the content
     */
    public Note(long patientId, LocalDate date, String content) {
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }

}
