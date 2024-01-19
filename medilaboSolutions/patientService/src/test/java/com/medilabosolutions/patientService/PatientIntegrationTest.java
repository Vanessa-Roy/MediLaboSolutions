package com.medilabosolutions.patientService;

import com.medilabosolutions.patientService.repository.PatientRepository;
import com.medilabosolutions.patientService.service.PatientServiceDefaultImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PatientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientServiceDefaultImpl patientService;

    private final String patientId = "1";

    public static String loadJson(String fileName) {
        try {
            Resource data = new ClassPathResource(fileName);
            return Files.readString(data.getFile().toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.5-bullseye"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .waitingFor(Wait.forListeningPort())
            .withEnv("POSTGRES_ROOT_HOST", "%");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @BeforeEach
    void setUpTest() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,  "patients");
        jdbcTemplate.execute("INSERT INTO patients(id, firstname, lastname, birthdate, gender, address, phone) VALUES(1,'Test','TestNone','1966-12-31','F','1 Brookside St','1002223333'); INSERT INTO patients(id, firstname, lastname, birthdate, gender, address, phone) VALUES(2,'Test','TestBorderline','1945-06-24','M','2 High St','2003334444');");
    }

    @Test
    void getPatientsShouldReturnTheListOfPatientCasesTest() {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/patients",
                String.class)).contains(loadJson("PatientCases.json"));
    }

    @Test
    void getPatientByIdShouldReturnOneOfThePatientCasesTest() {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/patient?id=" + patientId ,
                String.class)).contains(loadJson("OneOfThePatientCases.json"));
    }

    @Test
    void updatePatientByIdShouldReturnOneOfThePatientCasesTest() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(loadJson("UpdateOneOfThePatientCases.json")))
                .uri(URI.create("http://localhost:" + port + "/updatePatient"))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/patient?id=" + patientId ,
                String.class)).contains(loadJson("UpdateOneOfThePatientCases.json"));
    }
}
