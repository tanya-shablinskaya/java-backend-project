package com.gmail.puhovashablinskaya.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controller.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.EmployeeDetailsService;
import com.gmail.puhovashablinskaya.service.SessionService;
import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.config.AuthRequestValidatorConfig;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
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

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegistrationEmployeeController.class)
class RegistrationEmployeeControllerTest {
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
    private ServiceConfig serviceConfig;
    @MockBean
    private UserService userService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    private DataTimeService dataTimeService;

    @Test
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(get("/api/v1/auth/signin"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnErrorWhenInvalidContentType() throws Exception {
        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void shouldReturnSuccessWhenAddValidUser() throws Exception {
        UserAddDTO user = validUser();

        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsNull() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username(null)
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();
        mockValidateConstants();


        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIsRussianLanguage() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("фффффффф")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();
        mockValidateConstants();


        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithNumbers() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("admin5")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithUppercase() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("Adminn")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIs5Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("admin")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();
        mockValidateConstants();


        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username length must be more than 6 and less than 100 characters", errors.get(0));
    }

    @Test
    void shouldReturnSuccessWhenUsernameIs100() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("a".repeat(100))
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorWhenUsernameIsMoreThen100() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("admin".repeat(21))
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username length must be more than 6 and less than 100 characters", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameWithSpecialCharacter() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("admin&")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIsSpaces() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("      ")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Username should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenUsernameIsNotUnique() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("qwerty")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Фол")
                .build();

        mockValidateConstants();
        when(userService.isUsernameUnique(anyString())).thenReturn(false);

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Пользователь существует. Укажите другой логин", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsNull() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Password should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenPasswordIsConsistOfSpaces() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("       ")
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Password should be not null", errors.get(0));
    }

    @Test
    void shouldReturnSuccessWhenPasswordIs8Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("12345678")
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorWhenPasswordLessThan8() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("12345")
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Password length should be from 6 to 20 characters", errors.get(0));
    }

    @Test
    void shouldSuccessWhenPasswordIs20Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("1".repeat(20))
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorWhenPasswordIsMoreThan20Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("1".repeat(21))
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Password length should be from 6 to 20 characters", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenEmailIsNull() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Usermail should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenEmailIsEmpty() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("              ")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Usermail should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenInvalidEmail() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91")
                .firstName("Фол")
                .build();
        mockValidateConstants();


        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorWhenEmailIsNotPatternEqual() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Usermail does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenEmailIsNotHasDomainName() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@bsb")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Usermail does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenEmailIsNotHasUserPart() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("@bsb.vy")
                .firstName("Фол")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Usermail does not fit the given format", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenInvalidFirstname() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("QQQ")
                .build();
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnErrorWhenFirstnameIsNull() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Name should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenFirstnameIsEmpty() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("      ")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Name should be not null", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenFirstnameIsEnglishLanguage() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("Ivanoff")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Name should be written in Russian", errors.get(0));
    }

    @Test
    void shouldReturnSuccessWhenFirstnameIs20Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("ф".repeat(20))
                .build();
        mockValidateConstants();

        mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnErrorWhenFirstnameIs21Symbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("ф".repeat(21))
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Name length must be less than 20 characters", errors.get(0));
    }

    @Test
    void shouldReturnErrorWhenFirstnameHasSpecificSymbols() throws Exception {
        UserAddDTO user = UserAddDTO.builder()
                .username("username")
                .password("qqqq123456")
                .usermail("viexoat91@mail.ru")
                .firstName("ф%%%")
                .build();
        mockValidateConstants();

        MvcResult mvcResult = mvc.perform(post("/api/v1/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(actualResponseBody, Map.class);
        List<String> errors = (List<String>) map.get("errors");
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("Name should be written in Russian", errors.get(0));
    }


    private UserAddDTO validUser() {
        return UserAddDTO.builder()
                .username("userrrrrr")
                .password("qqqqqqqqqq")
                .usermail("viexoat91@gmail.com")
                .firstName("Фол")
                .build();
    }

    private void mockValidateConstants() {
        when(authRequestConfig.getLoginNameMaxLength()).thenReturn(100);
        when(authRequestConfig.getLoginNameMinLength()).thenReturn(6);
        when(authRequestConfig.getRegexLoginNameLatineChar()).thenReturn("\\w+");
        when(authRequestConfig.getRegexLoginNameDigits()).thenReturn("\\d+");
        when(authRequestConfig.getRegexLoginNameCharLowerCase()).thenReturn("^[a-z]+$");
        when(userService.isUsernameUnique(anyString())).thenReturn(true);
        when(authRequestConfig.getPasswordMaxLength()).thenReturn(20);
        when(authRequestConfig.getPasswordMinLength()).thenReturn(8);
        when(authRequestConfig.getRegexFormatMail()).thenReturn("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        when(authRequestConfig.getMailMaxLength()).thenReturn(100);
        when(userService.isUsermailUnique(anyString())).thenReturn(true);
        when(authRequestConfig.getRegexFirstNameRusChar()).thenReturn("^[А-я]+$");
        when(serviceConfig.getMaxLengthFirstName()).thenReturn(20);
    }
}
