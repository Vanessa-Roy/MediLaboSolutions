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
    public List<PatientDTO> getPatients() {
        return patientService.getPatients().stream().map(patientMapper::from).toList();
    }

    @GetMapping("/patients/{id}")
    public PatientDTO getPatientById(@PathVariable String id) {
        return patientMapper.from(patientService.getPatientById(id));
    }

    @PutMapping("/patients/{id}")
    public void updatePatient(@RequestBody PatientDTO patientDTO, @PathVariable String id) {
        patientService.updatePatient(patientMapper.to(patientDTO));
    }


}