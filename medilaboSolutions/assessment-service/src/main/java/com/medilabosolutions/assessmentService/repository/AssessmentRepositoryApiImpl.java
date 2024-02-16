package com.medilabosolutions.assessmentService.repository;

import com.medilabosolutions.assessmentService.controller.dtos.NoteDto;
import com.medilabosolutions.assessmentService.controller.dtos.PatientDTO;
import com.medilabosolutions.assessmentService.mapper.NoteMapper;
import com.medilabosolutions.assessmentService.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Repository
public class AssessmentRepositoryApiImpl implements AssessmentRepository {
    @Autowired
    ApiRequestBuilder apiRequestBuilder;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public PatientDTO getPatientById(Long id) throws Exception {
        HttpRequest request = apiRequestBuilder.getRequest("/patients/" + id);
        HttpResponse<String> response = apiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(response.statusCode());
        return patientMapper.fromStringToPatient(response.body());
    }

    @Override
    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        HttpRequest request = apiRequestBuilder.getRequest("/notes/" + patientId);
        HttpResponse<String> response = apiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(response.statusCode());
        return noteMapper.toListNote(response.body());
    }

    public void checkIfStatusExpected(int status) throws Exception {
        if (status != 200) {
            if (status == 401) {
                throw new Exception("You don't have the authorization");
            } else {
                throw new Exception("An error occurred");
            }
        }
    }

}
