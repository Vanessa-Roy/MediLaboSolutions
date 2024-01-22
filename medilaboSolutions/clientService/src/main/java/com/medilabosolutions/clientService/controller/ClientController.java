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

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

@Controller
public class ClientController {

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping("/patients")
    public String getPatients(Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<PatientDTO> patientList = mapper.readValue(new URL(gatewayUrl + "/patients"), new TypeReference<>() {
        });
        patientList.sort(Comparator.comparing(PatientDTO::getId));
        model.addAttribute("patientList", patientList);
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String updatePatient(@PathVariable String id, Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PatientDTO patient = mapper.readValue(new URL(gatewayUrl + "/patients/" + id), new TypeReference<>() {
        });
        model.addAttribute("patient", patient);
        return "updatePatient";
    }

    @PostMapping("/patients/{id}")
    public String updatePatient(@PathVariable String id, @Valid @ModelAttribute("patient") PatientDTO patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePatient";
        }
        try {
            if (patient != null) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                String requestBody = mapper.writeValueAsString(patient);

                HttpRequest request = HttpRequest.newBuilder()
                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                        .uri(URI.create(gatewayUrl + "/patients/" + id))
                        .header("Content-Type", "application/json")
                        .build();

                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

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