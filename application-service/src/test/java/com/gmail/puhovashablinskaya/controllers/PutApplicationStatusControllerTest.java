package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.PutApplicationStatusService;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PutApplicationStatusController.class)
class PutApplicationStatusControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private DateTimeService dataTimeService;
    @MockBean
    private AuthCheckService authCheckService;
    @MockBean
    private PutApplicationStatusService putApplicationStatusService;

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValue() throws Exception {
        mvc.perform(put("/api/v1/applications")
                        .param("applicationConvId", "1")
                        .param("status", "NEW")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmEiLCJ1c2VySWQiOjEsImNoZWNrVXNlcklkIjp0cnVlLCJpYXQiOjE2NDgzMDc5MTMsImV4cCI6MTY0ODU2NzExM30.2mGtbhNu7ZveW7WbjYG3nI8yGxVwYB2sCR7o_D6r-vwVW1rJkoBHobZffCtwLHRkJ8eF8ui1ig7FrEPY0RAtkg"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(get("/api/v1/applications"))
                .andExpect(status().isMethodNotAllowed());
    }
}