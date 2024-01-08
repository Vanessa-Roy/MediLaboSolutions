package com.medilabosolutions.patientService;

import com.medilabosolutions.patientService.repository.PatientRepository;
import com.medilabosolutions.patientService.service.PatientServiceDefaultImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientServiceDefaultImpl patientService;

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
    void getPatientsShouldReturnTheListOfPatientsTest() {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/patients",
                String.class)).contains(loadJson("PatientTests.json"));
    }

    @Test
    void findAllShouldCallFindAllRepository() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        patientService.findAll();

        verify(patientRepository, Mockito.times(1)).findAll();
    }
}
