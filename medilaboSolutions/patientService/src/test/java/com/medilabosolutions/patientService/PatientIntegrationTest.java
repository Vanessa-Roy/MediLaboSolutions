package com.medilabosolutions.patientService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public static String loadJson(String fileName) {
        try {
            Resource data = new ClassPathResource(fileName);
            return Files.readString(data.getFile().toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getPatientsShouldReturnTheListOfPatientCasesTest() {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/patients",
                String.class)).contains(loadJson("PatientCases.json"));
    }
}
