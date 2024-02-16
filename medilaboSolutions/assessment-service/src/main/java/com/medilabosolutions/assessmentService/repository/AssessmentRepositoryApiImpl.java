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

/**
 * The type Assessment repository api.
 */
@Repository
public class AssessmentRepositoryApiImpl implements AssessmentRepository {
    /**
     * The Api request builder.
     */
    @Autowired
    ApiRequestBuilder apiRequestBuilder;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private NoteMapper noteMapper;

    /**
     * Gets patient by id.
     *
     * @param id the id
     * @return the patient by id
     * @throws Exception the exception
     */
    @Override
    public PatientDTO getPatientById(Long id) throws Exception {
        HttpRequest request = apiRequestBuilder.getRequest("/patients/" + id);
        HttpResponse<String> response = apiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(response.statusCode());
        return patientMapper.fromStringToPatient(response.body());
    }

    /**
     * Gets notes by patient id.
     *
     * @param patientId the patient id
     * @return the notes by patient id
     * @throws Exception the exception
     */
    @Override
    public List<NoteDto> getNotesByPatientId(long patientId) throws Exception {
        HttpRequest request = apiRequestBuilder.getRequest("/notes/" + patientId);
        HttpResponse<String> response = apiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(response.statusCode());
        return noteMapper.toListNote(response.body());
    }

    /**
     * Check if status expected.
     *
     * @param status the status
     * @throws Exception the exception
     */
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
