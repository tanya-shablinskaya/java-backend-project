package com.gmail.puhovashablinskaya.integration;

import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.ResidenceEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class LegalIT extends BaseIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.system}")
    private String jwt;

    @Test
    void addLegal() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        LegalAddDTO legalAddDTO = findLegal();

        HttpEntity<String> entity = new HttpEntity(legalAddDTO, headers);

        ResponseEntity<LegalAddResultDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/legals"),
                HttpMethod.POST,
                entity,
                LegalAddResultDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    @Sql({"/sql/legalById.sql"})
    void findLegalGetById() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<LegalDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/legals/4"),
                HttpMethod.GET,
                entity,
                LegalDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/getLegal.sql"})
    void getLegal() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        headers.add("name", "doremifa");
        headers.add("unp", "321222339");
        headers.add("IBANbyBYN", "BY00UNBS00011110003000000300");

        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/legals"),
                HttpMethod.GET,
                entity,
                List.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }


    private LegalAddDTO findLegal() {
        return LegalAddDTO.builder()
                .name("doremifasewi")
                .iban("BY00UNBS00011110003000333300")
                .unp(321226639)
                .countOfEmployees(50)
                .residence(ResidenceEnum.RESIDENT)
                .build();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
