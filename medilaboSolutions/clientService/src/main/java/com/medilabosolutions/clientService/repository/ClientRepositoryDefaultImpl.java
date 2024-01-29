package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
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

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<PatientDTO> getPatients() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients"))
                .header("Authorization", getAuthorizationValue())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        checkIfStatus401(response.statusCode());
        checkIfPatient(response.body());
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
        checkIfStatus401(response.statusCode());
        checkIfPatient(response.body());
        return patientMapper.toPatient(response.body());
    }

    @Override
    public void updatePatient(PatientDTO patient) throws Exception {
        String requestBody = patientMapper.fromPatient(patient);
        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(new URI(gatewayUrl + "/patients/" + patient.getId()))
                .header("Content-Type", "application/json")
                .header("Authorization", getAuthorizationValue())
                .build();
        checkIfStatus401(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode());
    }

    private void checkIfStatus401(int status) throws Exception {
        if (status == 401) {
            throw new Exception("You don't have the authorization");
        }
    }

    public void checkIfPatient(String response) throws Exception {
        if (response.isEmpty()) {
            throw new Exception("No Patient found");
        }
    }

    private String getAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + userPassword).getBytes());
    }

}
