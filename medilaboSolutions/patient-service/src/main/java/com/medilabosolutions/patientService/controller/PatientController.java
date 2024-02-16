package com.medilabosolutions.patientService.controller;

import com.medilabosolutions.patientService.controller.dtos.PatientDTO;
import com.medilabosolutions.patientService.mapper.PatientMapper;
import com.medilabosolutions.patientService.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Management of the requests though the controller.
 */
@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;

    /**
     * Gets patients.
     *
     * @return the patients
     */
    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getPatients() {
        try {
            return ResponseEntity.ok(patientService.getPatients().stream().map(patientMapper::from).toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(patientMapper.from(patientService.getPatientById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Update a patient.
     *
     * @param patientDTO the patient dto
     * @param id         the id
     * @return the response entity
     */
    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO, @PathVariable Long id) {
        try {
            patientService.updatePatient(patientMapper.to(patientDTO), id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}