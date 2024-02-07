package com.medilabosolutions.assessmentService;

import com.medilabosolutions.assessmentService.model.Assessment;
import com.medilabosolutions.assessmentService.repository.AssessmentRepository;
import com.medilabosolutions.assessmentService.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssessmentIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AssessmentService assessmentService;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void getAssessmentByIdPatientShouldReturnTheAssessmentOfThePatientTest() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:" + port + "/assessment/1"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200);
        assertEquals(response.body(), '"' + Assessment.NONE.name() + '"');
    }
}
