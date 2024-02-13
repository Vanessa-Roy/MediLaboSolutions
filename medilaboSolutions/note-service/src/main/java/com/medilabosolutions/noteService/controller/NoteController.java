package com.medilabosolutions.noteService.controller;

import com.medilabosolutions.noteService.controller.dtos.NoteDto;
import com.medilabosolutions.noteService.controller.dtos.NoteToCreateDto;
import com.medilabosolutions.noteService.mapper.NoteMapper;
import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import com.medilabosolutions.noteService.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    NoteMapper noteMapper;

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/{patientId}")
    public ResponseEntity<List<NoteDto>> getNotesByPatientId(@PathVariable long patientId) {
/*            MongoClient mongoClient = new MongoClientImpl(MongoClientSettings.builder().build(), MongoDriverInformation.builder().build());
            final MongoDatabase db = mongoClient.getDatabase("note-service");
            final BasicDBObject createUserCommand = new BasicDBObject("createUser", "user").append("pwd", "Vmfy59b8iW8X30zm").append("roles",
                    Collections.singletonList(new BasicDBObject("role", "readWrite").append("db", "note-service")));
            db.runCommand(createUserCommand);*/
/*        noteRepository.deleteAll();
        List<Note> noteList = new ArrayList<>();
        Note note1 = new Note(1, LocalDate.of(2024, 1,29), "Patient reports 'feeling very good' Weight at or below recommended weight");
        noteList.add(note1);
        Note note2 = new Note(2, LocalDate.of(2023, 1,29), "The patient states that he feels a lot of stress at work. He also complains that his hearing has been abnormal lately.");
        noteList.add(note2);
        Note note3 = new Note(2, LocalDate.of(2024,1,15), "The patient reports having had a reaction to medications over the past 3 months. He also notes that his hearing continues to be abnormal");
        noteList.add(note3);
        Note note2b = new Note(3, LocalDate.of(2023, 1,29), "The patient states that he has recently started smoking");
        noteList.add(note2b);
        Note note3b = new Note(3, LocalDate.of(2024,1,15), "Patient states he is a smoker and quit smoking last year He also complains of abnormal respiratory apnea attacks Laboratory tests indicating elevated LDL cholesterol");
        noteList.add(note3b);
        Note note2c = new Note(4, LocalDate.of(2023, 1,15), "Patient reports that it has become difficult for him to climb stairs He also complains of shortness of breath Laboratory tests indicating that antibodies are elevated Reaction to medications");
        noteList.add(note2c);
        Note note3c = new Note(4, LocalDate.of(2023,1,29), "Patient reports back pain when sitting for a long time");
        noteList.add(note3c);
        Note note2d = new Note(4, LocalDate.of(2024, 1,15), "Patient reports having recently started smoking Hemoglobin A1C above recommended level");
        noteList.add(note2d);
        Note note3d = new Note(4, LocalDate.of(2024,1,29), "Height, Weight, Cholesterol, Dizziness and Reaction");
        noteList.add(note3d);
        noteRepository.saveAll(noteList);*/
        return ResponseEntity.ok(noteService.getNotesByPatientId(patientId).stream().map(noteMapper::fromNote).toList());
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteToCreateDto noteToCreate, @PathVariable long patientId) {
        try {
            Note noteCreate = noteService.createNote(noteMapper.toNote(noteToCreate), patientId);
            return new ResponseEntity<>(noteMapper.fromNote(noteCreate), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
