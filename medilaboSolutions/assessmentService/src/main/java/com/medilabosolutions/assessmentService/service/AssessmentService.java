package com.medilabosolutions.assessmentService.service;

import com.medilabosolutions.assessmentService.model.Assessment;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {

    public Assessment getAssessment(Long patientId) {
        // TO DO return Assessment.EARLY_ONSET = F : age < 30 && trigger <= 7 || M : age < 30 && trigger <= 5;
        // TO DO return Assessment.IN_DANGER = F : age < 30 && trigger = 4 || M : age < 30 && trigger = 3 || age > 30 && 6 <= trigger <= 7 ;
        // TO DO return Assessment.BORDERLINE = age > 30 && 2 <= trigger <= 5 || age > 30 && trigger >= 8 ;
        // TO DO return Assessment.NONE;
        return Assessment.NONE;
    }

    // TO DO FUNCTION WHO GET THE PATIENT
    // TO DO FUNCTION WHO CKECK AGE
    // TO DO FUNCTION WHO CHECK GENDER
    // TO DO FUNCTION WHO GET THE NOTES
    // TO DO FUNCTION WHO CHECK THE NUMBER OF TRIGGER INTO THE CONTENT

}
