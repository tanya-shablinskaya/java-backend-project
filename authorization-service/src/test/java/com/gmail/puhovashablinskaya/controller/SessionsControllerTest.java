package com.gmail.puhovashablinskaya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controller.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.EmployeeDetailsService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SessionsController.class)
class SessionsControllerTest {
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
    private SessionService sessionService;
    @MockBean
    private UserService userService;
    @MockBean
    private DataTimeService dataTimeService;
    @MockBean
    private EmployeeDetailsService employeeDetailsService;


    @Test
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        String jwt = "qwertyuioqwe";
        mvc.perform(put("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(jwt))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnErrorWhenJwtIsNull() throws Exception {
        String jwt = null;
        mvc.perform(post("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(objectMapper.writeValueAsString(jwt)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenIsNotActiveSession() throws Exception {
        String jwt = "qwertyuioqwe";
        when(sessionService.isActiveSession(jwt)).thenReturn(false);
        when(sessionService.findUsernameByJwt(jwt)).thenReturn("qqqq");
        when(sessionService.isActiveSessionByUsername("qqqq")).thenReturn(true);

        mvc.perform(post("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(jwt))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenIsUsernameNull() throws Exception {
        String jwt = "qwertyuioqwe";
        when(sessionService.isActiveSession(jwt)).thenReturn(true);
        when(sessionService.findUsernameByJwt(jwt)).thenReturn(null);
        when(sessionService.isActiveSessionByUsername(null)).thenReturn(false);

        mvc.perform(post("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(jwt))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenisActiveSessionByUsername() throws Exception {
        String jwt = "qwertyuioqwe";
        when(sessionService.isActiveSession(jwt)).thenReturn(true);
        when(sessionService.findUsernameByJwt(jwt)).thenReturn("qqqq");
        when(sessionService.isActiveSessionByUsername("qqqq")).thenReturn(false);

        mvc.perform(post("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(jwt))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnU200WhenValidBusinessProcess() throws Exception {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJJZCI6MTIsImNoZWNrVXNlcklkIjpmYWxzZSwiaWF0IjoxNjQ3NzA5MDcyLCJleHAiOjE2NzkyNDUwNzJ9." +
                "B5dIMeAyekBz7sZUxhnMo1zLZjLosZsfNCmcrKMSv7_4UBDBrgEf_wmQQQCAvP4MnJY_MoBq0ju43BYCecSlZg";
        when(sessionService.isActiveSession(jwt)).thenReturn(true);
        when(jwtUtils.validateJwtToken(jwt)).thenReturn(true);
        when(sessionService.findUsernameByJwt(jwt)).thenReturn("qqqq");
        when(userService.isActiveUser("qqqq")).thenReturn(true);
        when(sessionService.isActiveSessionByUsername("qqqq")).thenReturn(true);

        mvc.perform(post("/api/v1/auth/sessions")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(jwt))
                .andExpect(status().isOk());
    }


}
