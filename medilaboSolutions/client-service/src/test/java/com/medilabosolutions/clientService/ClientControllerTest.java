package com.medilabosolutions.clientService;

import com.medilabosolutions.clientService.controller.ClientController;
import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @InjectMocks
    private ClientController clientController;
    @Mock
    private ClientService clientService;

    private PatientDTO patientDTO;
    private Model model;

    private BindingResult bindingResult;


    @BeforeEach
    void setUpPerTest() {
        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        model = new ConcurrentModel();
        bindingResult = new BeanPropertyBindingResult(patientDTO, "patient");
    }

    @Test
    void getPatientsShouldReturnTheTemplatePatientsTest() throws Exception {
        when(clientService.getPatients()).thenReturn(new ArrayList<>());

        String result = clientController.getPatients(model);

        verify(clientService, times(1)).getPatients();
        assertEquals(result, "patients");
        assertEquals(new ArrayList<>(), model.getAttribute("patientList"));
    }

    @Test
    void getPatientsWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        when(clientService.getPatients()).thenThrow(new Exception("my message"));

        String result = clientController.getPatients(model);

        verify(clientService, times(1)).getPatients();
        assertEquals(result, "error");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void getPatientsDetailsShouldReturnTheTemplatePatientDetailsTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);
        when(clientService.getNotesByPatientId(1L)).thenReturn(new ArrayList<>());
        when(clientService.getAssessment(1L)).thenReturn(Assessment.NONE);

        String result = clientController.getPatientDetails(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, times(1)).getNotesByPatientId(1L);
        verify(clientService, times(1)).getAssessment(1L);
        assertEquals(result, "patientDetails");
        assertEquals(patientDTO, model.getAttribute("patient"));
        assertEquals(new ArrayList<>(), model.getAttribute("noteList"));
        assertEquals(Assessment.NONE, model.getAttribute("assessment"));
    }

    @Test
    void getPatientsDetailsWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        when(clientService.getNotesByPatientId(1L)).thenThrow(new Exception("my message"));

        String result = clientController.getPatientDetails(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, times(1)).getNotesByPatientId(1L);
        verify(clientService, never()).getAssessment(1L);
        assertEquals(result, "patientDetails");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void getPatientsDetailsWithNotAcceptableStatusExceptionShouldReturnTheTemplatePatientDetailsTest() throws Exception {
        when(clientService.getPatientById(1L)).thenThrow(new NotAcceptableStatusException("my message"));

        String result = clientController.getPatientDetails(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, never()).getNotesByPatientId(1L);
        verify(clientService, never()).getAssessment(1L);
        assertEquals(result, "error");
        assertEquals("406 NOT_ACCEPTABLE \"my message\"", model.getAttribute("errorMessage"));
    }

    @Test
    void getUpdatePatientShouldReturnTheTemplateUpdatePatientTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);

        String result = clientController.updatePatient(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        assertEquals(result, "updatePatient");
        assertEquals(patientDTO, model.getAttribute("patient"));
    }

    @Test
    void getUpdatePatientWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        when(clientService.getPatientById(1L)).thenThrow(new Exception("my message"));

        String result = clientController.updatePatient(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        assertEquals(result, "error");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void postUpdatePatientShouldReturnTheTemplatePatientsTest() throws Exception {
        doNothing().when(clientService).updatePatient(patientDTO, 1L);

        String result = clientController.updatePatient(1L, patientDTO, bindingResult, model);

        verify(clientService, times(1)).updatePatient(patientDTO, 1L);
        assertEquals(result, "redirect:/patients/{id}?successUpdate");
    }

    @Test
    void postUpdatePatientWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        doThrow(new Exception("my message")).when(clientService).updatePatient(patientDTO, 1L);

        String result = clientController.updatePatient(1L, patientDTO, bindingResult, model);

        verify(clientService, times(1)).updatePatient(patientDTO, 1L);
        assertEquals(result, "error");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void postUpdatePatientWithBindingResultShouldReturnTheTemplateUpdatePatientTest() throws Exception {
        bindingResult.addError(new ObjectError("patient", "error"));

        String result = clientController.updatePatient(1L, patientDTO, bindingResult, model);

        verify(clientService, never()).updatePatient(patientDTO, 1L);
        assertEquals(result, "updatePatient");
    }

    @Test
    void getAddANoteFormShouldReturnTheTemplateAddNoteTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);

        String result = clientController.getAddANoteForm(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        assertEquals(result, "addNote");
        assertEquals(patientDTO, model.getAttribute("patient"));
        assertEquals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), model.getAttribute("localDate"));
    }

    @Test
    void getAddANoteFormWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        when(clientService.getPatientById(1L)).thenThrow(new Exception("my message"));

        String result = clientController.getAddANoteForm(1L, model);

        verify(clientService, times(1)).getPatientById(1L);
        assertEquals(result, "error");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void postAddNoteToPatientShouldReturnTheTemplatePatientsTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);
        doNothing().when(clientService).addNoteToPatient(any(NoteDto.class));

        String result = clientController.addNoteToPatient(1L, "content", LocalDate.now(), model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, times(1)).addNoteToPatient(any(NoteDto.class));
        assertEquals(result, "redirect:/patients/{id}?successNote");
    }

    @Test
    void postAddNoteToPatientWithoutDateShouldReturnTheTemplatePatientsTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);
        doNothing().when(clientService).addNoteToPatient(any(NoteDto.class));

        String result = clientController.addNoteToPatient(1L, "content", null, model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, times(1)).addNoteToPatient(any(NoteDto.class));
        assertEquals(result, "redirect:/patients/{id}?successNote");
    }

    @Test
    void postAddNoteToPatientWithExceptionShouldReturnTheTemplateErrorTest() throws Exception {
        doThrow(new Exception("my message")).when(clientService).getPatientById(1L);

        String result = clientController.addNoteToPatient(1L, "content", LocalDate.now(), model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, never()).addNoteToPatient(any(NoteDto.class));
        assertEquals(result, "error");
        assertEquals("my message", model.getAttribute("errorMessage"));
    }

    @Test
    void postAddNoteToPatientWithNullNoteShouldReturnTheTemplateAddNoteTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);

        String result = clientController.addNoteToPatient(1L, null, LocalDate.now(), model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, never()).addNoteToPatient(any(NoteDto.class));
        assertEquals(result, "addNote");
        assertEquals(patientDTO, model.getAttribute("patient"));
    }

    @Test
    void postAddNoteToPatientWithIncorrectNoteShouldReturnTheTemplateAddNoteTest() throws Exception {
        when(clientService.getPatientById(1L)).thenReturn(patientDTO);

        String result = clientController.addNoteToPatient(1L, "   ", LocalDate.now(), model);

        verify(clientService, times(1)).getPatientById(1L);
        verify(clientService, never()).addNoteToPatient(any(NoteDto.class));
        assertEquals(result, "addNote");
        assertEquals(patientDTO, model.getAttribute("patient"));
    }



}

