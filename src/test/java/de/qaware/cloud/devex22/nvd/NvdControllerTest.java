package de.qaware.cloud.devex22.nvd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NvdControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    void shouldRetrieveLog4JCVE() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port + "/api/cves/CVE-2021-44228", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
