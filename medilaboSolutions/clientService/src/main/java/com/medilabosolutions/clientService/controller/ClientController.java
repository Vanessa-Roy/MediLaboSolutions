package com.medilabosolutions.clientService.controller;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/patients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping()
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

    @GetMapping("/{id}")
    public String getPatientDetails(@PathVariable Long id, Model model) {
        try {
            PatientDTO patient = clientService.getPatientById(id);
            model.addAttribute("patient", patient);
            List<NoteDto> noteList = clientService.getNotesByPatientId(id);
            model.addAttribute("noteList", noteList);
            return "patientDetails";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/details")
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

    @PostMapping("/{id}")
    public String updatePatient(@PathVariable Long id, @Valid @ModelAttribute("patient") PatientDTO patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "updatePatient";
        }
        try {
            clientService.updatePatient(patient, id);
            return "redirect:/patients/{id}?successUpdate";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/notes")
    public String getAddANoteForm(@PathVariable Long id, Model model) {
        try {
            PatientDTO patient = clientService.getPatientById(id);
            model.addAttribute("patient", patient);
            model.addAttribute("localDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return "addNote";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/notes")
    public String addNoteToPatient(@PathVariable (name = "id") Long patientId, String content, LocalDate date, Model model) {
        try {
            if (date == null) {
                date = LocalDate.now();
            }
            if (content == null) {
                throw new Exception ("Content is null");
            }
            NoteDto noteToCreate = new NoteDto(patientId, date, content);
            clientService.addNoteToPatient(noteToCreate);
            return "redirect:/patients/{id}?successNote";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}