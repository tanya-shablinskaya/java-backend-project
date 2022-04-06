package com.gmail.puhovashablinskaya.integration;

import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByIdService;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.AddEmployeeService;
import com.gmail.puhovashablinskaya.service.FindEmployeesService;
import com.gmail.puhovashablinskaya.service.GetEmployeeByIdService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeIT extends BaseIT {
    @MockBean
    private FindLegalsByIdService findLegalsByIdService;
    @MockBean
    private FindEmployeesService findEmployeesService;
    @MockBean
    private AddEmployeeService addEmployeeService;
    @MockBean
    private GetEmployeeByIdService getEmployeeByIdService;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.system}")
    private String jwt;

    @Test
    void addEmployee() {
        EmployeeAddDTO employee = findEmployee();
        EmployeeDTO employeeDTO = findEmployeeDTO();
        EmployeeAddResultDTO employeeAddResultDTO = EmployeeAddResultDTO.builder().employee(employeeDTO).build();
        when(addEmployeeService.addEmployee(employee)).thenReturn(employeeAddResultDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(employee, headers);

        ResponseEntity<EmployeeAddResultDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/employees"),
                HttpMethod.POST,
                entity,
                EmployeeAddResultDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    @Sql({"/sql/employeeById.sql"})
    void findEmployeeGetById() {
        EmployeeDTO employeeDTObyID = findEmployeeDTObyId();
        when(getEmployeeByIdService.getById(3L)).thenReturn(employeeDTObyID);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/employees/3"),
                HttpMethod.GET,
                entity,
                EmployeeDTO.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/getEmployee.sql"})
    void getEmployees() {
        LegalDTO legal = LegalDTO.builder().id(2L).unp(321223339).name("doremifa").countOfEmployees(50).residence(ResidenceEnum.RESIDENT).build();
        when(findLegalsByIdService.getLegalById(2L)).thenReturn(legal);
        SearchEmployeeDTO searchEmployeeDTO = SearchEmployeeDTO.builder().employeeName("rrrrrwwwwww").legalName("doremifa").unp("321223339").build();
        List<EmployeeDTO> employeeDTO = new ArrayList<>();
        EmployeeDTO employeeDTO1 = EmployeeDTO.builder().legalName("doremifa").name("rrrrrwwwwww").ibanByn("BY00UNBS00001100000111000012").ibanCurrency("BY00UNBS00000100000000022111").id(2L).recruitmentDate(null).build();
        employeeDTO.add(employeeDTO1);
        when(findEmployeesService.findEmployees(searchEmployeeDTO)).thenReturn(employeeDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        headers.add("Name_Legal", "doremifa");
        headers.add("UNP", "321223339");
        headers.add("Full_Name_Individual", "rrrrrwwwwww");


        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<List> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/employees?Name_Legal=doremifa&UNP=321223339&Full_Name_Individual=rrrrrwwwwww"),
                HttpMethod.GET,
                entity,
                List.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }


    private EmployeeAddDTO findEmployee() {
        return EmployeeAddDTO.builder()
                .name("rrrrrrrrrrr")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("doremifa")
                .ibanByn("BY00UNBS00000000000111000000")
                .ibanCurrency("BY00UNBS00000100000000000000")
                .build();
    }

    private EmployeeDTO findEmployeeDTO() {
        return EmployeeDTO.builder()
                .name("rrrrrrrrrrr")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("doremifa")
                .ibanByn("BY00UNBS00000000000111000000")
                .ibanCurrency("BY00UNBS00000100000000000000")
                .build();

    }

    private EmployeeDTO findEmployeeDTObyId() {
        return EmployeeDTO.builder()
                .id(3L)
                .name("rrrrrrrrrer")
                .recruitmentDate(LocalDate.now())
                .terminationDate(LocalDate.now().plusDays(5L))
                .legalName("doremifa")
                .ibanByn("BY00UNBS00000000000111000012")
                .ibanCurrency("BY00UNBS00000100000000001111")
                .build();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
