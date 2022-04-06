package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controllers.config.ControllerValuesConfig;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.GetLegalsByIdService;
import com.gmail.puhovashablinskaya.service.exceptions.LegalException;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LegalGetByIdController.class)
class LegalGetByIdControllerTest {
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
    private GetLegalsByIdService getLegalsByIdService;
    @MockBean
    private DateTimeService dataTimeService;


    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValueAndUrlAndMethodAndContentType() throws Exception {
        mvc.perform(get("/api/v1/legals/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidId() throws Exception {
        when(getLegalsByIdService.getLegalById(1L)).thenThrow(LegalException.class);
        mvc.perform(get("/api/v1/legals/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(post("/api/v1/legals/{id}", 1))
                .andExpect(status().isMethodNotAllowed());
    }
}