package com.medilabosolutions.clientService.controller;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Management of the requests though the controller.
 */
@Controller
@RequestMapping("/patients")
public class ClientController {

    /**
     * The Client service.
     */
    @Autowired
    ClientService clientService;

    /**
     * Gets patients.
     *
     * @param model the model
     * @return the patients
     */
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

    /**
     * Gets patient details.
     *
     * @param id    the id
     * @param model the model
     * @return the patient details
     */
    @GetMapping("/{id}")
    public String getPatientDetails(@PathVariable Long id, Model model) {
        try {
            PatientDTO patient = clientService.getPatientById(id);
            model.addAttribute("patient", patient);
            List<NoteDto> noteList = clientService.getNotesByPatientId(id);
            model.addAttribute("noteList", noteList);
            Assessment assessment = clientService.getAssessment(id);
            model.addAttribute("assessment", assessment);
            return "patientDetails";
        } catch (NotAcceptableStatusException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "patientDetails";
        }
    }

    /**
     * Gets the form to Update a patient.
     *
     * @param id    the id
     * @param model the model
     * @return the string
     */
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

    /**
     * Update a patient.
     *
     * @param id            the id
     * @param patient       the patient
     * @param bindingResult the binding result
     * @param model         the model
     * @return the string
     */
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

    /**
     * Gets the form to add a note.
     *
     * @param id    the id
     * @param model the model
     * @return the add a note form
     */
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

    /**
     * Add note to patient.
     *
     * @param patientId the patient id
     * @param content   the content
     * @param date      the date
     * @param model     the model
     * @return the string
     */
    @PostMapping("/{id}/notes")
    public String addNoteToPatient(@PathVariable (name = "id") Long patientId, String content, LocalDate date, Model model) {
        try {
            PatientDTO currentPatient = clientService.getPatientById(patientId);
            if (date == null) {
                date = LocalDate.now();
            }
            if (content == null || content.isBlank()) {
                model.addAttribute("errorMessage", "content must contains letters");
                model.addAttribute("patient", currentPatient);
                model.addAttribute("localDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                return "addNote";
            }
            NoteDto noteToCreate = new NoteDto(currentPatient.getId(), date, content);
            clientService.addNoteToPatient(noteToCreate);
            return "redirect:/patients/{id}?successNote";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}