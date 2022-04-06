package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controllers.util.config.ValuesConfig;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.AddEmployeeService;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AddEmployeeController.class)
class AddEmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private AddEmployeeService addEmployeeService;
    @MockBean
    private AuthCheckService authCheckService;
    @MockBean
    private ValuesConfig valuesConfig;
    @MockBean
    private DateTimeService dataTimeService;

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValueAndUrlAndMethodAndContentType() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Иванов Иван")
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturnValidMessageWhenValidValueAndUrlAndMethodAndContentType() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Иванов Иван")
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturnValidMessageWhenValidValues() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Иванов Иван")
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();
        EmployeeAddResultDTO resultDTO = EmployeeAddResultDTO.builder()
                .employee(EmployeeDTO.builder().build())
                .message("Сотрудник успешно создан")
                .build();
        when(addEmployeeService.addEmployee(employee)).thenReturn(resultDTO);

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(resultDTO));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMediaType() throws Exception {
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidRequest() throws Exception {
        mockMvc.perform(get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenNameIsNull() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenNameIsBlank() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("         ")
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenNameIsMoreThan100() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(101))
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenNameIs100() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(100))
                .recruitmentDate(LocalDate.parse("2021-10-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenRequirementDateIsNull() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenRequirementDateIsAfterTodayDate() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2044-11-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenRequirementDateIsAfterTerminationDate() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2046-11-10"))
                .terminationDate(LocalDate.parse("2045-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }


    @Test
    @WithMockUser
    void shouldReturnErrorWhenTerminationDateIsNull() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2021-11-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenTerminationDateIsBeforeToday() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2021-11-10"))
                .terminationDate(LocalDate.parse("2021-12-10"))
                .ibanByn("BY86UNBS10100000002966000228")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenIbanBynIsNull() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2021-11-10"))
                .terminationDate(LocalDate.parse("2045-12-10"))
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenIbanBynIsNotEqualsPattern() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2021-11-10"))
                .terminationDate(LocalDate.parse("2045-12-10"))
                .ibanByn("BY86UNBS1")
                .ibanCurrency("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Неверно заданы параметры", errors.get(0));
    }

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenIbanCurrencyIsNull() throws Exception {
        EmployeeAddDTO employee = EmployeeAddDTO.builder()
                .name("Ф".repeat(10))
                .recruitmentDate(LocalDate.parse("2021-11-10"))
                .terminationDate(LocalDate.parse("2045-12-10"))
                .ibanByn("BY86UNBS10100000002966000229")
                .legalName("Company")
                .build();

        when(valuesConfig.getMaxLengthEmployeeName()).thenReturn(100);
        when(valuesConfig.getIbanPattern()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(valuesConfig.getEmployeeNamePattern()).thenReturn("^(?!\\s*)[\\u0410-\\u044F]+$");

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }
}