package com.medilabosolutions.clientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-unauthorizedUser")
public class ClientControllerUnauthorizedUserTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldDenyPatientsViewForUnauthorizedUserTest() throws Exception {
        this.mockMvc
                .perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void shouldDenyUpdatePatientViewForUnauthorizedUserTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    void shouldNotUpdatePatientForUnauthorizedUserTest() throws Exception {
        this.mockMvc
                .perform(post("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname","firstname")
                        .param("lastname","lastname")
                        .param("birthdate", LocalDate.now().toString())
                        .param("gender","F"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}
