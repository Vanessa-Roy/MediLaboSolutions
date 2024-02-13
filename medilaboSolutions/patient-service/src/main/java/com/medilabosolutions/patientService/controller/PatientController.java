package com.medilabosolutions.patientService.controller;

import com.medilabosolutions.patientService.controller.dtos.PatientDTO;
import com.medilabosolutions.patientService.mapper.PatientMapper;
import com.medilabosolutions.patientService.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMapper patientMapper;

    @Operation(summary = "Get all the patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the patients from the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getPatients() {
        try {
            return ResponseEntity.ok(patientService.getPatients().stream().map(patientMapper::from).toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(patientMapper.from(patientService.getPatientById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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