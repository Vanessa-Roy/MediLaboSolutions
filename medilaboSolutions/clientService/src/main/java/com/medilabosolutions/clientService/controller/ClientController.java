package com.medilabosolutions.clientService.controller;

import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/patients")
    public String getPatients(Model model) {
        try {
            List<PatientDTO> patientList = clientService.getPatients();
            model.addAttribute("patientList", patientList);
            return "patients";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/patients/{id}")
    public String updatePatient(@PathVariable Long id, Model model) {
        try {
            PatientDTO patient = clientService.getPatientById(id);
            model.addAttribute("patient", patient);
            return "updatePatient";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/patients/{id}")
    public String updatePatient(@PathVariable Long id, @Valid @ModelAttribute("patient") PatientDTO patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePatient";
        }
        try {
            clientService.updatePatient(patient, id);
            return "redirect:/patients?success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}