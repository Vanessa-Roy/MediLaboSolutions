package com.medilabosolutions.clientService.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Controller
public class ClientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String userPassword;

    public static ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @GetMapping("/patients")
    public String getPatients(Model model) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String valueToEncode = username + ":" + userPassword;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients"))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401) {
            model.addAttribute("errorMessage", "You don't have the authorization");
            return "error";
        }
        try {
            List<PatientDTO> patientList = mapper().readValue(response.body(), new TypeReference<>() {
            });
            patientList.sort(Comparator.comparing(PatientDTO::getId));
            model.addAttribute("patientList", patientList);
            return "patients";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/patients/{id}")
    public String updatePatient(@PathVariable String id, Model model) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String valueToEncode = username + ":" + userPassword;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(gatewayUrl + "/patients/" + id))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 401) {
            model.addAttribute("errorMessage", "You don't have the authorization");
            return "error";
        }
        try {
            PatientDTO patient = mapper().readValue(response.body(), new TypeReference<>() {
            });
            model.addAttribute("patient", patient);
            return "updatePatient";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/patients/{id}")
    public String updatePatient(@PathVariable String id, @Valid @ModelAttribute("patient") PatientDTO patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePatient";
        }
        try {
            if (patient != null && Long.valueOf(id).equals(patient.getId())) {

                String requestBody = mapper().writeValueAsString(patient);

                HttpClient client = HttpClient.newHttpClient();
                String valueToEncode = username + ":" + userPassword;
                HttpRequest request = HttpRequest.newBuilder()
                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                        .uri(new URI(gatewayUrl + "/patients/" + id))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 401) {
                    model.addAttribute("errorMessage", "You don't have the authorization");
                    return "error";
                }
                return "redirect:/patients?success";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("patient", patient);
            return "updatePatient";
        }
        return "updatePatient";
    }
}