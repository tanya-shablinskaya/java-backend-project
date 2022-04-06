package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controllers.config.ControllerValuesConfig;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.GetLegalsFactory;
import com.gmail.puhovashablinskaya.service.exceptions.PaginationException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LegalsGetController.class)
class LegalsGetControllerTest {
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
    private ControllerValuesConfig config;
    @MockBean
    private AuthCheckService authCheckService;
    @MockBean
    private GetLegalsFactory getLegalsFactory;
    @MockBean
    private DateTimeService dataTimeService;

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValueAndUrlAndMethod() throws Exception {
        mvc.perform(get("/api/v1/legals"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnListLegalsWhenGet() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder()
                .pagination(PaginationEnum.DEFAULT)
                .page(1)
                .build();
        SearchLegalDTO searchLegalDTO = SearchLegalDTO.builder().build();
        List<LegalDTO> result = List.of(LegalDTO.builder().build());

        when(getLegalsFactory.getLegals(pagination, searchLegalDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/legals")
                        .param("pagination", "DEFAULT")
                        .param("page", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }

    @Test
    @WithMockUser
    void shouldReturnListLegalsWhenSearchByName() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder().build();
        SearchLegalDTO searchLegalDTO = SearchLegalDTO.builder()
                .name("aaa")
                .build();
        List<LegalDTO> result = List.of(LegalDTO.builder().build());

        when(getLegalsFactory.getLegals(pagination, searchLegalDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/legals")
                        .param("name", "aaa"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }

    @Test
    @WithMockUser
    void shouldReturnListLegalsWhenSearchByUnp() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder().build();
        SearchLegalDTO searchLegalDTO = SearchLegalDTO.builder()
                .unp("123")
                .build();
        List<LegalDTO> result = List.of(LegalDTO.builder().build());

        when(getLegalsFactory.getLegals(pagination, searchLegalDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/legals")
                        .param("unp", "123"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }

    @Test
    @WithMockUser
    void shouldReturnListLegalsWhenSearchByIban() throws Exception {
        PaginationDTO pagination = PaginationDTO.builder().build();
        SearchLegalDTO searchLegalDTO = SearchLegalDTO.builder()
                .iban("BY3")
                .build();
        List<LegalDTO> result = List.of(LegalDTO.builder().build());

        when(getLegalsFactory.getLegals(pagination, searchLegalDTO)).thenReturn(result);

        MvcResult mvcResult = mvc.perform(get("/api/v1/legals")
                        .param("IBANbyBYN", "BY3"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(result));
    }

    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchIsJoin() throws Exception {
        mvc.perform(get("/api/v1/legals")
                        .param("pagination", "DEFAULT")
                        .param("name", "Company"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }

    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchByUnpIsJoin() throws Exception {
        mvc.perform(get("/api/v1/legals")
                        .param("pagination", "DEFAULT")
                        .param("unp", "Company"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }

    @Test
    @WithMockUser
    void shouldThrowExceptionWhenPaginationAndSearchByIbanIsJoin() throws Exception {
        mvc.perform(get("/api/v1/legals")
                        .param("pagination", "DEFAULT")
                        .param("IBANbyBYN", "Company"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PaginationException));
    }
}