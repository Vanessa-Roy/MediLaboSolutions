package com.medilabosolutions.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-unauthorizedUser")
public class ClientControllerUnauthorizedUserTest {

    @Autowired
    private MockMvc mockMvc;
/*    @Test
    void shouldDenyPatientsViewForUnauthorizedUserTest() throws Exception {
        this.mockMvc
                .perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/

/*    @Test
    void shouldDenyUpdatePatientViewForUnauthorizedUserTest() throws Exception {
        this.mockMvc
                .perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("errorMessage"));
    }*/

/*    @Test
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
    }*/
}
