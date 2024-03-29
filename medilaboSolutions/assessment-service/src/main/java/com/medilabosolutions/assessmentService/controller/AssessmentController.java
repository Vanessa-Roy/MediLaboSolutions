package com.medilabosolutions.assessmentService.controller;

import com.medilabosolutions.assessmentService.enums.Assessment;
import com.medilabosolutions.assessmentService.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Management of the requests though the controller.
 */
@RestController
public class AssessmentController {

    /**
     * The Assessment service.
     */
    @Autowired
    AssessmentService assessmentService;

    /**
     * Gets assessment.
     *
     * @param patientId the patient id
     * @param userId the user id
     * @return the assessment
     */
    @GetMapping("/assessment/{patientId}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable long patientId, @RequestParam String userId) {
        try {
            return ResponseEntity.ok(assessmentService.getAssessment(patientId, userId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
