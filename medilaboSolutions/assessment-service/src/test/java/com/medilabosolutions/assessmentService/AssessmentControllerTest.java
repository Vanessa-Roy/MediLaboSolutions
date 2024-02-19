package com.medilabosolutions.assessmentService;

import com.medilabosolutions.assessmentService.controller.AssessmentController;
import com.medilabosolutions.assessmentService.enums.Assessment;
import com.medilabosolutions.assessmentService.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssessmentControllerTest {
    @InjectMocks
    private AssessmentController assessmentController;
    @Mock
    private AssessmentService assessmentService;

    @Test
    void getAssessmentByIdPatientShouldReturnTheAssessmentOfThePatientTest() throws Exception {
        when(assessmentService.getAssessment(1L, "user")).thenReturn(Assessment.NONE);

        ResponseEntity<Assessment> result = assessmentController.getAssessment(1L, "user");

        verify(assessmentService, times(1)).getAssessment(1L, "user");
        assertEquals(result, ResponseEntity.ok(Assessment.NONE));
    }

    @Test
    void getAssessmentByIdPatientWithExceptionShouldReturnErrorTest() throws Exception {
        when(assessmentService.getAssessment(1L, "user")).thenThrow(Exception.class);

        ResponseEntity<Assessment> result = assessmentController.getAssessment(1L, "user");

        assertEquals(result, ResponseEntity.internalServerError().build());
    }
}
