package com.gmail.puhovashablinskaya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.controller.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.*;
import com.gmail.puhovashablinskaya.service.config.AuthRequestValidatorConfig;
import com.gmail.puhovashablinskaya.service.validators.AuthRequestValidator;
import com.gmail.puhovashablinskaya.service.validators.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private SessionService sessionService;
    @MockBean
    private EmployeeDetailsService detailsService;
    @MockBean
    private AuthRequestValidatorConfig authRequestConfig;
    @MockBean
    private UserService userService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    private DataTimeService dataTimeService;
    @MockBean
    private AuthRequestValidator authRequestValidator;
    @MockBean
    private AuthService authService;

    @Test
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(get("/api/v1/auth/login"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnErrorWhenInvalidContentType() throws Exception {
        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void shouldReturnSuccessWhenAddValidUser() throws Exception {
        AuthRequest user = authRequestValid();
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsNull() throws Exception {
        AuthRequest authRequest = new AuthRequest(null, "wwwwwwwww");
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithNumbers() throws Exception {
        AuthRequest authRequest = new AuthRequest("qwee323eq", "wwwwwwwww");
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithUppercase() throws Exception {
        AuthRequest authRequest = new AuthRequest("WWWWWWWWW", "wwwwwwwww");
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIs5Symbols() throws Exception {
        AuthRequest authRequest = new AuthRequest("qqqqq", "wwwwwwwww");
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnSuccessWhenUsernameIs100() throws Exception {
        AuthRequest authRequest = new AuthRequest("qqqqq".repeat(20), "wwwwwwwww");
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsMoreThen100() throws Exception {
        AuthRequest user = new AuthRequest("qqqqq".repeat(21), "wwwwwwwww");
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithSpecialCharacter() throws Exception {
        AuthRequest user = new AuthRequest("admin&", "wwwwwwwww");
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIsSpaces() throws Exception {
        AuthRequest user = new AuthRequest(" ", "wwwwwwwww");
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsNull() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", null);
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsConsistOfSpaces() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", "   ");
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnSuccessWhenPasswordIs8Symbols() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", "12345678");
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnErrorWhenPasswordLessThan8() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", "12345");
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldSuccessWhenPasswordIs20Symbols() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", "1".repeat(20));
        mockValidateConstants();
        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnErrorWhenPasswordIsMoreThan20Symbols() throws Exception {
        AuthRequest user = new AuthRequest("wwwwwwwww", "1".repeat(21));
        mockValidateConstants();
        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenInvalidEmail() throws Exception {
        AuthRequest user = new AuthRequest("viexoat91", "aaaaqaaaa");
        mockValidateConstants();
        mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorWhenEmailIsNotPatternEqual() throws Exception {
        AuthRequest user = new AuthRequest("viexoat91", "aaaaqaaaa");
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Login or password is not valid", errors.get(0));
    }

    private void mockValidateConstants() {
        when(authRequestConfig.getLoginNameMaxLength()).thenReturn(100);
        when(authRequestConfig.getLoginNameMinLength()).thenReturn(6);
        when(authRequestConfig.getRegexLoginNameLatineChar()).thenReturn("\\w+");
        when(authRequestConfig.getRegexLoginNameDigits()).thenReturn("\\d+");
        when(authRequestConfig.getRegexLoginNameCharLowerCase()).thenReturn("^[a-z]+$");
        when(authRequestConfig.getPasswordMaxLength()).thenReturn(20);
        when(authRequestConfig.getPasswordMinLength()).thenReturn(8);
        when(authRequestConfig.getRegexFormatMail()).thenReturn("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        when(authRequestConfig.getMailMaxLength()).thenReturn(100);
    }

    private AuthRequest authRequestValid() {
        return new AuthRequest(
                "qqqqqqqqq", "ppppppppppp"
        );
    }
}