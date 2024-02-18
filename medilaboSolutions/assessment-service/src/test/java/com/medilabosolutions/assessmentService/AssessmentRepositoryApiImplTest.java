package com.medilabosolutions.assessmentService;

import com.medilabosolutions.assessmentService.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.mapper.NoteMapper;
import com.medilabosolutions.assessmentService.mapper.PatientMapper;
import com.medilabosolutions.assessmentService.repository.ApiRequestBuilder;
import com.medilabosolutions.assessmentService.repository.AssessmentRepositoryApiImpl;
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
public class AssessmentRepositoryApiImplTest {

    @InjectMocks
    AssessmentRepositoryApiImpl assessmentRepositoryApi;

    @Mock
    ApiRequestBuilder apiRequestBuilder;

    public HttpRequest getRequest;

    @Mock
    HttpResponse<String> response;
    @Mock
    PatientMapper patientMapper;
    @Mock
    NoteMapper noteMapper;

    @BeforeEach
    void SetUpPerTest() throws URISyntaxException {
        getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("http://localhost:9999"))
                .build();
    }

    @Test
    void getPatientByIdShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder.getRequest("/patients/1?userId=user")).thenReturn(getRequest);
        when(apiRequestBuilder.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(patientMapper.fromStringToPatient(response.body())).thenReturn(new PatientDTO());

        assertNotNull(assessmentRepositoryApi.getPatientById("user", 1L));

        verify(apiRequestBuilder, times(1)).getRequest("/patients/1?userId=user");
        verify(apiRequestBuilder, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(patientMapper, times(1)).fromStringToPatient(response.body());
    }

    @Test
    void getNotesByIdShouldCallApiMethods() throws Exception {
        when(apiRequestBuilder.getRequest("/notes/1")).thenReturn(getRequest);
        when(apiRequestBuilder.getStringHttpResponse(getRequest)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        when(noteMapper.toListNote(response.body())).thenReturn(new ArrayList<>());

        assertNotNull(assessmentRepositoryApi.getNotesByPatientId(1L));

        verify(apiRequestBuilder, times(1)).getRequest("/notes/1");
        verify(apiRequestBuilder, times(1)).getStringHttpResponse(getRequest);
        verify(response, times(1)).statusCode();
        verify(noteMapper, times(1)).toListNote(response.body());
    }

    @Test
    void checkIfStatusExpectedNotExpectedShouldThrowException() {
        Exception exception = assertThrows(Exception.class, () -> assessmentRepositoryApi.checkIfStatusExpected(500));

        assertEquals("An error occurred", exception.getMessage());
    }



}

