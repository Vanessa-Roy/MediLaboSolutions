package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.mapper.AssessmentMapper;
import com.medilabosolutions.clientService.mapper.NoteMapper;
import com.medilabosolutions.clientService.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

@Repository
public class ClientRepositoryDefaultImpl implements ClientRepository {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String userPassword;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private AssessmentMapper assessmentMapper;

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<PatientDTO> getPatients() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients"))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(200, response.statusCode());
        checkIfBody(response.body());
        return patientMapper.toListPatient(response.body());
    };

    @Override
    public PatientDTO getPatientById(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients/" + id))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(200, response.statusCode());
        checkIfBody(response.body());
        return patientMapper.fromStringToPatient(response.body());
    }

    @Override
    public void updatePatient(PatientDTO patient) throws Exception {
        String requestBody = patientMapper.fromPatientToString(patient);
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(new URI(gatewayUrl + "/patients/" + patient.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", getAuthorizationValue())
                .build();
        checkIfStatusExpected(200, client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode());
    }

    @Override
    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/notes/" + patientId))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(200, response.statusCode());
        checkIfBody(response.body());
        return noteMapper.toListNote(response.body());
    }

    @Override
    public void addNoteToPatient(NoteDto note) throws Exception {
        String requestBody = noteMapper.fromNoteToString(note);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(new URI(gatewayUrl + "/notes/" + note.patientId))
                .header("Content-Type", "application/json")
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(201, response.statusCode());
        checkIfBody(response.body());
    }

    @Override
    public Assessment getAssessment(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/assessment/" + id))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(200, response.statusCode());
        checkIfBody(response.body());
        return assessmentMapper.fromStringToAssessment(response.body());
    }

    private void checkIfStatusExpected(int statusExpected, int status) throws Exception {
        if (status != statusExpected) {
            if (status == 401) {
                throw new Exception("You don't have the authorization");
            } else if (status == 400) {
                throw new Exception("Bad request");
            } else {
                throw new Exception("An error occurred");
            }
        }
    }

    public void checkIfBody(String response) throws Exception {
        if (response.isEmpty()) {
            throw new Exception("No body found");
        }
    }

    private String getAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + userPassword).getBytes());
    }

}
