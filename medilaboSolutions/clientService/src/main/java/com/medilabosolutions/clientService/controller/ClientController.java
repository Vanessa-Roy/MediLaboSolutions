package com.medilabosolutions.clientService.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
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


}