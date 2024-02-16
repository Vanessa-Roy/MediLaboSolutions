package com.medilabosolutions.clientService;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.mapper.AssessmentMapper;
import com.medilabosolutions.clientService.mapper.NoteMapper;
import com.medilabosolutions.clientService.mapper.PatientMapper;
import com.medilabosolutions.clientService.repository.ApiRequestBuilder;
import com.medilabosolutions.clientService.repository.ClientRepositoryApiImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientRepositoryApiImplTest {

    @InjectMocks
    ClientRepositoryApiImpl clientRepositoryApi;

    @Mock
    ApiRequestBuilder apiRequestBuilder1;

    public HttpRequest getRequest;
    public HttpRequest putRequest;
    public HttpRequest postRequest;

    @Mock
    HttpResponse<String> response;
    @Mock
    PatientMapper patientMapper;
    @Mock
    NoteMapper noteMapper;
    @Mock
    AssessmentMapper assessmentMapper;


    @BeforeEach
    void SetUpPerTest() throws URISyntaxException {
        getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:9999"))
                .header("Authorization", "Basic username:userPassword")
                .build();
        putRequest = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .uri(new URI("http://localhost:9999"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic username:userPassword")
                .build();
        postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(new URI("http://localhost:9999"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic username:userPassword")
                .build();
    }

    @Test
    void getPatientsShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder1.getRequest("/patients")).thenReturn(getRequest);
        when(apiRequestBuilder1.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(patientMapper.toListPatient(response.body())).thenReturn(new ArrayList<>());

        assertNotNull(clientRepositoryApi.getPatients());

        verify(apiRequestBuilder1, times(1)).getRequest("/patients");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(patientMapper, times(1)).toListPatient(response.body());
    }

    @Test
    void getPatientByIdShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder1.getRequest("/patients/1")).thenReturn(getRequest);
        when(apiRequestBuilder1.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(patientMapper.fromStringToPatient(response.body())).thenReturn(new PatientDTO());

        assertNotNull(clientRepositoryApi.getPatientById(1L));

        verify(apiRequestBuilder1, times(1)).getRequest("/patients/1");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(patientMapper, times(1)).fromStringToPatient(response.body());
    }

    @Test
    void updatePatientByIdShouldCallApiMethods() throws Exception {

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        when(patientMapper.fromPatientToString(patientDTO)).thenReturn("");
        when(apiRequestBuilder1.putRequest("/patients/1", "")).thenReturn(putRequest);
        when(apiRequestBuilder1.getStringHttpResponse(putRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);

        clientRepositoryApi.updatePatient(patientDTO);

        verify(patientMapper, times(1)).fromPatientToString(patientDTO);
        verify(apiRequestBuilder1, times(1)).putRequest("/patients/1", "");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(putRequest);
        verify(response, times(1)).statusCode();
    }

    @Test
    void getNotesByIdShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder1.getRequest("/notes/1")).thenReturn(getRequest);
        when(apiRequestBuilder1.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(noteMapper.toListNote(response.body())).thenReturn(new ArrayList<>());

        assertNotNull(clientRepositoryApi.getNotesByPatientId(1L));

        verify(apiRequestBuilder1, times(1)).getRequest("/notes/1");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(noteMapper, times(1)).toListNote(response.body());
    }

    @Test
    void addNoteToPatientShouldCallApiMethods() throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setPatientId(1L);
        when(noteMapper.fromNoteToString(noteDto)).thenReturn("");
        when(apiRequestBuilder1.postRequest("/notes/1", "")).thenReturn(postRequest);
        when(apiRequestBuilder1.getStringHttpResponse(postRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(201);

        clientRepositoryApi.addNoteToPatient(noteDto);

        verify(noteMapper, times(1)).fromNoteToString(noteDto);
        verify(apiRequestBuilder1, times(1)).postRequest("/notes/1", "");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(postRequest);
        verify(response, times(1)).statusCode();
    }

    @Test
    void getAssessmentShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder1.getRequest("/assessment/1")).thenReturn(getRequest);
        when(apiRequestBuilder1.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(assessmentMapper.fromStringToAssessment(response.body())).thenReturn(Assessment.NONE);

        assertNotNull(clientRepositoryApi.getAssessment(1L));

        verify(apiRequestBuilder1, times(1)).getRequest("/assessment/1");
        verify(apiRequestBuilder1, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(assessmentMapper, times(1)).fromStringToAssessment(response.body());
    }

    @Test
    void checkIfStatusExpected401ShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> clientRepositoryApi.checkIfStatusExpected(200, 401, ""));

        assertEquals("406 NOT_ACCEPTABLE \"You don't have the authorization\"", exception.getMessage());
    }

    @Test
    void checkIfStatusExpected404ShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> clientRepositoryApi.checkIfStatusExpected(200, 404, ""));

        assertEquals("406 NOT_ACCEPTABLE \"Element not found\"", exception.getMessage());
    }

    @Test
    void checkIfStatusExpectedNotExpectedShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> clientRepositoryApi.checkIfStatusExpected(200, 500, "yourMicroservice"));

        assertEquals("An error occurred during the request of the yourMicroservice", exception.getMessage());
    }



}
