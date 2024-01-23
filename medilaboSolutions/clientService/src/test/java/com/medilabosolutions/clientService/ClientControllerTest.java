package com.medilabosolutions.clientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDisplayPatientsViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients"))
                .andExpect(model().attributeExists("patientList"));
    }

    @Test
    void shouldDisplayUpdatePatientViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updatePatient"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void shouldDenyUpdateNonExistentPatientViewTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void shouldUpdateExistentPatientTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname","firstname")
                        .param("lastname","lastname")
                        .param("birthdate", LocalDate.now().toString())
                        .param("gender","F"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients?success"));
    }

    @Test
    void shouldNotUpdatePatientWithoutMandatoryValueTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname","firstname"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("updatePatient"));
    }

    @Test
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
    }
    

}
