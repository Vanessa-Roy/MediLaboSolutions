package com.medilabosolutions.assessmentService.repository;

import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.mapper.NoteMapper;
import com.medilabosolutions.assessmentService.mapper.PatientMapper;
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
public class AssessmentRepositoryDefaultImpl implements AssessmentRepository {

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

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public PatientDTO getPatientById(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients/" + id))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(response.statusCode());
        return patientMapper.fromStringToPatient(response.body());
    }

    @Override
    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/notes/" + patientId))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatusExpected(response.statusCode());
        return noteMapper.toListNote(response.body());
    }

    private void checkIfStatusExpected(int status) throws Exception {
        if (status != 200) {
            if (status == 401) {
                throw new Exception("You don't have the authorization");
            } else {
                throw new Exception("An error occurred");
            }
        }
    }

    private String getAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + userPassword).getBytes());
    }

}
