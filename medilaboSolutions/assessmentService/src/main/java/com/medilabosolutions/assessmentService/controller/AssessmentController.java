package com.medilabosolutions.assessmentService.controller;

import com.medilabosolutions.assessmentService.model.Assessment;
import com.medilabosolutions.assessmentService.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessmentController {

    @Autowired
    AssessmentService assessmentService;
    @GetMapping("/assessment/{patientId}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable long patientId) {
        return ResponseEntity.ok(assessmentService.getAssessment(patientId));
    }
}
