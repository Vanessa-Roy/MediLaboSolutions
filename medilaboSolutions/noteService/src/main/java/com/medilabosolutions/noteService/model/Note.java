package com.medilabosolutions.noteService.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("notes")
@Getter
@Setter
public class Note {
    @Id
    private String id;
    private long patientId;
    private LocalDate date;
    private String content;

    public Note(long patientId, LocalDate date, String content) {
        this.patientId = patientId;
        this.date = date;
        this.content = content;
    }
}
