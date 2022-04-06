package com.gmail.puhovashablinskaya.integration;

import com.gmail.puhovashablinskaya.repository.feign.request.FindEmployeeInfoService;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByIdService;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalByNameService;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.GetAppService;
import com.gmail.puhovashablinskaya.service.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ApplicationsIT extends BaseIT {
    @LocalServerPort
    private int port;
    @MockBean
    private FindEmployeeInfoService findEmployeeInfoService;
    @MockBean
    private FindLegalByIdService findLegalByIdService;
    @MockBean
    private FindLegalByNameService findLegalByNameService;
    @MockBean
    private GetAppService getAppService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.system}")
    private String jwt;

    @Test
    void fileUpload() {
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        body.add("file", new org.springframework.core.io.ClassPathResource("readyFile.csv"));

        when(findEmployeeInfoService.getEmployeeById(22L)).thenReturn(findEmployee());
        when(findEmployeeInfoService.getEmployeeById(23L)).thenReturn(findOneEmployee());


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/files"),
                HttpMethod.POST,
                entity,
                String.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/putStatus.sql"})
    void putApplicationStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/applications?applicationConvId=3&status=IN_PROGRESS"),
                HttpMethod.PUT,
                entity,
                Object.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/applicationById.sql"})
    void findApplicationById() {
        LegalDTO legalDTO = findLegal();
        when(findEmployeeInfoService.getEmployeeById(5L)).thenReturn(findEmployeeByAppID());
        when(findLegalByIdService.getLegalById(3l)).thenReturn(legalDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<ApplicationDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/applications/6"),
                HttpMethod.GET,
                entity,
                ApplicationDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/changeApp.sql"})
    void putApplicationChange() {
        when(findLegalByNameService.getLegalsByInfo("privetandre")).thenReturn(findListLegal());
        Optional<LegalDTO> optionalLegalDTO = findListLegal().stream()
                .filter(legalDTO -> legalDTO.getName().equals("privetandre"))
                .findFirst();
        String name = optionalLegalDTO.get().getName();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        headers.add("Name_Legal", name);

        HttpEntity<Object> entity = new HttpEntity(headers);

        ResponseEntity<ApplicationChangeResultDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/applications/7?Name_Legal=privetandre"),
                HttpMethod.PUT,
                entity,
                ApplicationChangeResultDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/getApplication.sql"})
    void getEApplication() {
        when(getAppService.getApplicationsList(1, 1)).thenReturn(findListApp());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        headers.add("pagination", "DEFAULT");
        headers.add("page", "1");


        HttpEntity<Object> entity = new HttpEntity(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/applications?pagination=DEFAULT&page=1"),
                HttpMethod.GET,
                entity,
                List.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    private List<ApplicationDTO> findListApp() {
        ApplicationDTO applicationDTO = ApplicationDTO.builder()
                .applicationId("a1a78316-b82a-4c7b-b0a3-a7bdbdae4f94")
                .currencyFrom(CurrencyEnumDTO.BYN)
                .currencyTo(CurrencyEnumDTO.USD)
                .employeeId(9L)
                .employeeName("name")
                .status(StatusApplicationEnumDTO.NEW)
                .percent(0.5F)
                .legalName("doremifawer")
                .build();
        return List.of(applicationDTO);
    }

    private EmployeeDTO findEmployee() {
        return EmployeeDTO.builder()
                .id(22L)
                .name("rrrrrrrrrrr")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("nnnnnnnn")
                .ibanByn("BY00UNBS00000000000111000000")
                .ibanCurrency("BY00UNBS00000100000000000000")
                .build();
    }

    private EmployeeDTO findOneEmployee() {
        return EmployeeDTO.builder()
                .id(23L)
                .name("Arrrrrrrrrr")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("ffffffff")
                .ibanByn("BY00UNBS00000010000111000000")
                .ibanCurrency("BY00UNBS01000100000000000000")
                .build();
    }

    private EmployeeDTO findEmployeeByAppID() {
        return EmployeeDTO.builder()
                .id(5L)
                .name("Arrrrrrrrt")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("doremifasewi")
                .ibanByn("BY00UNBS00000010000111000020")
                .ibanCurrency("BY00UNBS01000100000000200000")
                .build();
    }

    private LegalDTO findLegal() {
        return LegalDTO.builder()
                .id(3l)
                .name("doremifasewi")
                .iban("BY00UNBS00011110003000333300")
                .unp(321226639)
                .countOfEmployees(50)
                .residence("RESIDENT")
                .build();
    }

    private List<LegalDTO> findListLegal() {
        LegalDTO legalDTO = LegalDTO.builder()
                .id(10L)
                .name("privetandre")
                .iban("BY00UNBS00011110003000353300")
                .unp(321337739)
                .countOfEmployees(50)
                .residence("RESIDENT")
                .build();
        return List.of(legalDTO);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}