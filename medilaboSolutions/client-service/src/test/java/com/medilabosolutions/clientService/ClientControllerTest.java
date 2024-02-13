package com.medilabosolutions.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-unauthorizedUser")
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

/*    @Test
    void shouldDisplayPatientsViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patientList"));
    }*/

/*    @Test
    void shouldDisplayUpdatePatientViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePatient"))
                .andExpect(model().attributeExists("patient"));
    }*/

/*    @Test
    void shouldDisplayDetailsPatientViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patientDetails"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("noteList"))
                .andExpect(model().attributeExists("assessment"));
    }*/

/*    @Test
    void shouldDisplayAddNoteViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("addNote"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("localDate"));
    }*/

/*
    @Test
    void shouldDenyDisplayAddNoteViewWithNonExistentPatientTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/5/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
*/

/*    @Test
    void shouldDenyDisplayDetailsPatientsViewWithNonExistentPatientTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/

/*    @Test
    void shouldDenyUpdateNonExistentPatientViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/5/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/

/*    @Test
    void shouldNotCreateNoteWithNonExistentPatientTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/5/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", LocalDate.now().toString())
                        .param("content","content"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/

/*    @Test
    void shouldNotCreateNoteWithoutMandatoryValueTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date",LocalDate.now().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("addNote"));
    }*/

/*    @Test
    void shouldNotCreateNoteWithIncorrectValueTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("content","12345")
                        .param("date",LocalDate.now().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("addNote"));
    }*/

/*    @Test
    void shouldNotUpdatePatientWithoutMandatoryValueTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname","firstname"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("updatePatient"));
    }*/

/*    @Test
    void shouldNotUpdateNonExistentPatientTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname","firstname")
                        .param("lastname","lastname")
                        .param("birthdate", LocalDate.now().toString())
                        .param("gender","F"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/


}
