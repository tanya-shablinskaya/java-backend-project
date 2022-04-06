package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controllers.util.config.ValuesConfig;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.GetEmployeeDivideService;
import com.gmail.puhovashablinskaya.service.exception.PaginationException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeesGetController.class)
class EmployeesGetControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private GetEmployeeDivideService getEmployeeDivideService;
    @MockBean
    private DateTimeService dataTimeService;
    @MockBean
    private AuthCheckService authCheckService;
    @MockBean
    private ValuesConfig valuesConfig;


    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValue() throws Exception {
        mvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(put("/api/v1/employees"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser
    void shouldReturnListEmployeeWhenGet() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder()
                .pagination(PaginationEnum.DEFAULT)
                .page(1)
                .build();
        SearchEmployeeDTO searchEmployeeDTO = SearchEmployeeDTO.builder().build();
        List<EmployeeDTO> result = List.of(EmployeeDTO.builder().build());

        when(getEmployeeDivideService.getEmployees(pagination, searchEmployeeDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/employees")
                        .param("pagination", "DEFAULT")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }

    @Test
    @WithMockUser
    void shouldReturnListEmployeeWhenSearch() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder().build();
        SearchEmployeeDTO searchEmployeeDTO = SearchEmployeeDTO.builder()
                .legalName("aaa")
                .build();
        List<EmployeeDTO> result = List.of(EmployeeDTO.builder().build());

        when(getEmployeeDivideService.getEmployees(pagination, searchEmployeeDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/employees")
                        .param("Name_Legal", "aaa"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }


    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchIsJoin() throws Exception {
        mvc.perform(get("/api/v1/employees")
                        .param("pagination", "DEFAULT")
                        .param("Name_Legal", "Company"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }

    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchUnp() throws Exception {
        mvc.perform(get("/api/v1/employees")
                        .param("pagination", "DEFAULT")
                        .param("UNP", "123456789"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }

    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchEmployeeName() throws Exception {
        mvc.perform(get("/api/v1/employees")
                        .param("pagination", "DEFAULT")
                        .param("Full_Name_Individual", "Name"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }

}