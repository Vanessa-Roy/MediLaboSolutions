package com.medilabosolutions.noteService.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("notes")
@Getter
public class Note {
    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    private long patientId;
    private LocalDate date;
    private String content;

    public Note() {
    }
    public Note(long patientId, LocalDate date, String content) {
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }

}
