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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
public class ClientController {

    @Value("${gateway.url}")
    private String gatewayUrl;
    @GetMapping("/patients")
    public String getPatients(Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<PatientDTO> patientList = mapper.readValue(new URL(gatewayUrl + "/patients"), new TypeReference<>() {});
        model.addAttribute("patientList", patientList);
        return "patients";
    }

    @GetMapping("/updatePatient")
    public String updatePatient(@RequestParam String id, Model model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        PatientDTO patient = mapper.readValue(new URL(gatewayUrl + "/patient?id=" + id), new TypeReference<>() {});
        model.addAttribute("patient", patient);
        return "updatePatient";
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@Valid @ModelAttribute("patient") PatientDTO patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePatient";
        }
        try {
            URL url = new URL(gatewayUrl + "/updatePatient?id=" + patient.getId());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            return "redirect:/patients?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("patient", patient);
            return "updatePatient";
        }
    }

}