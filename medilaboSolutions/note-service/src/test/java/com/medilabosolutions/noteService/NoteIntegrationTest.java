package com.medilabosolutions.noteService;

import com.medilabosolutions.noteService.model.Note;
import com.medilabosolutions.noteService.repository.NoteRepository;
import com.medilabosolutions.noteService.service.NoteServiceDefaultImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteServiceDefaultImpl noteService;

    @Autowired
    MongoTemplate mongoTemplate;

    private final String patientId = "1";

    public static String loadJson(String fileName) {
        try {
            Resource data = new ClassPathResource(fileName);
            return Files.readString(data.getFile().toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    @BeforeEach
    void setUpTest() {
        mongoDBContainer.start();
        Note note1 = new Note(1, LocalDate.of(2024, 1,29), "content test1");
        note1.setId("id1");
        mongoTemplate.save(note1);
        Note note2 = new Note(1, LocalDate.of(2024,1,29), "content test2");
        note2.setId("id2");
        mongoTemplate.save(note2);
        Note note3 = new Note(2, LocalDate.of(2024,1,29), "content test3");
        note3.setId("id3");
        mongoTemplate.save(note3);
    }

    @AfterEach
    void closingUpTest() {
        noteRepository.deleteAll();
    }

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void getNotesByIdPatientShouldReturnTheListOfNotesByIdPatientTest() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:" + port  + "/notes/" + patientId))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
        assertEquals(response.body(), loadJson("NotesByPatient.json"));
    }


    @Test
    void createNoteByPatientIdShouldReturnTheNoteTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(loadJson("CreateOneOfTheNoteByPatientId.json")))
                .uri(URI.create("http://localhost:" + port + "/notes/" + patientId))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> result = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(result.statusCode(), 201);
        List<Note> noteList = mongoTemplate.findAll(Note.class);
        assertEquals(4,noteList.size());
    }

    @Test
    void createNoteByPatientIdWithEmptyBodyShouldNotReturnTheNoteTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create("http://localhost:" + port + "/notes/" + patientId))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> result = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(result.statusCode(), 400);
        List<Note> noteList = mongoTemplate.findAll(Note.class);
        assertEquals(3,noteList.size());
    }

    @Test
    void createNoteByPatientIdWithIncorrectPatientShouldNotReturnTheNoteTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(loadJson("CreateOneOfTheNoteByPatientId.json")))
                .uri(URI.create("http://localhost:" + port + "/notes/" + 2))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> result = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(result.statusCode(), 500);
        List<Note> noteList = mongoTemplate.findAll(Note.class);
        assertEquals(3,noteList.size());
    }
}
