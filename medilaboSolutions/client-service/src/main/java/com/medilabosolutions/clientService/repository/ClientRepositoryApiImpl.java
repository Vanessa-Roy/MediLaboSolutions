package com.medilabosolutions.clientService.repository;

import com.medilabosolutions.clientService.controller.dtos.NoteDto;
import com.medilabosolutions.clientService.controller.dtos.PatientDTO;
import com.medilabosolutions.clientService.controller.dtos.enums.Assessment;
import com.medilabosolutions.clientService.mapper.AssessmentMapper;
import com.medilabosolutions.clientService.mapper.NoteMapper;
import com.medilabosolutions.clientService.mapper.PatientMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.NotAcceptableStatusException;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * The type Client repository api.
 */
@Repository
public class ClientRepositoryApiImpl implements ClientRepository {

    private final PatientMapper patientMapper;
    private final NoteMapper noteMapper;
    private final ApiRequestBuilder getApiRequestBuilder;
    private final AssessmentMapper assessmentMapper;

    /**
     * Instantiates a new Client repository api.
     *
     * @param patientMapper        the patient mapper
     * @param noteMapper           the note mapper
     * @param assessmentMapper     the assessment mapper
     * @param getApiRequestBuilder the get api request builder
     */
    public ClientRepositoryApiImpl(PatientMapper patientMapper, NoteMapper noteMapper, AssessmentMapper assessmentMapper, ApiRequestBuilder getApiRequestBuilder) {
        this.patientMapper = patientMapper;
        this.noteMapper = noteMapper;
        this.assessmentMapper = assessmentMapper;
        this.getApiRequestBuilder = getApiRequestBuilder;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     * @throws Exception the exception
     */
    @Override
    public List<PatientDTO> getPatients() throws Exception {
        HttpRequest request = getApiRequestBuilder.getRequest("/patients");
        HttpResponse<String> response = getApiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(200, response.statusCode(), "patients");
        return patientMapper.toListPatient(response.body());
    }

    /**
     * Gets patient by id.
     *
     * @param id the patient id
     * @return the patient by id
     * @throws Exception the exception
     */
    @Override
    public PatientDTO getPatientById(Long id) throws Exception {
        HttpRequest request = getApiRequestBuilder.getRequest("/patients/" + id);
        HttpResponse<String> response = getApiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(200, response.statusCode(), "patient");
        return patientMapper.fromStringToPatient(response.body());
    }

    /**
     * Update patient.
     *
     * @param patient the patient
     * @throws Exception the exception
     */
    @Override
    public void updatePatient(PatientDTO patient) throws Exception {
        String requestBody = patientMapper.fromPatientToString(patient);
        HttpRequest request = getApiRequestBuilder.putRequest("/patients/" + patient.getId(), requestBody);
        checkIfStatusExpected(200, getApiRequestBuilder.getStringHttpResponse(request).statusCode(), "updating patient");
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
        HttpRequest request = getApiRequestBuilder.getRequest("/notes/" + patientId);
        HttpResponse<String> response = getApiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(200, response.statusCode(), "note");
        return noteMapper.toListNote(response.body());
    }

    /**
     * Add note to patient.
     *
     * @param note the note
     * @throws Exception the exception
     */
    @Override
    public void addNoteToPatient(NoteDto note) throws Exception {
        String requestBody = noteMapper.fromNoteToString(note);
        HttpRequest request = getApiRequestBuilder.postRequest( "/notes/" + note.getPatientId(), requestBody);
        HttpResponse<String> response = getApiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(201, response.statusCode(), "adding a note");
    }

    /**
     * Gets assessment.
     *
     * @param id the patient id
     * @return the assessment
     * @throws Exception the exception
     */
    @Override
    public Assessment getAssessment(Long id) throws Exception {
        HttpRequest request = getApiRequestBuilder.getRequest("/assessment/" + id);
        HttpResponse<String> response = getApiRequestBuilder.getStringHttpResponse(request);
        checkIfStatusExpected(200, response.statusCode(), "assessment");
        return assessmentMapper.fromStringToAssessment(response.body());
    }

    /**
     * Check if status expected.
     *
     * @param statusExpected the status expected
     * @param status         the status
     * @param request        the request
     * @throws Exception the exception
     */
    public void checkIfStatusExpected(int statusExpected, int status, String request) throws Exception {
        if (status != statusExpected) {
            if (status == 401) {
                throw new NotAcceptableStatusException("You don't have the authorization");
            } else if (status == 404) {
                throw new NotAcceptableStatusException("Element not found");
            } else {
                throw new Exception("An error occurred during the request of the " + request);
            }
        }
    }

}
