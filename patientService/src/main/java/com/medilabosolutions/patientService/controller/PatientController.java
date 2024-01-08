package com.medilabosolutions.patientService.controller;

import com.medilabosolutions.patientService.model.Patient;
import com.medilabosolutions.patientService.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientService.findAll();
    }


}