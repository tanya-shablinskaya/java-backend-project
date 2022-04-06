package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.controllers.config.ControllerValuesConfig;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.AddLegalService;
import com.gmail.puhovashablinskaya.service.GetLegalByNotUniqueFields;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.exceptions.LegalNotUniqueException;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.ResidenceEnum;
import com.gmail.puhovashablinskaya.util.DateTimeService;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LegalAddController.class)
class LegalAddControllerTest {
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
    private AddLegalService addLegalService;
    @MockBean
    private ControllerValuesConfig config;
    @MockBean
    private GetLegalByNotUniqueFields getLegalByUniqueFields;
    @MockBean
    private AuthCheckService authCheckService;
    @MockBean
    private DateTimeService dataTimeService;

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValueAndUrlAndMethodAndContentType() throws Exception {
        LegalAddDTO legalAddDTO = LegalAddDTO.builder()
                .countOfEmployees(1)
                .name("woman")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();
        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalAddDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalAddDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturnValidMessageWhenValidValueAndUrlAndMethodAndContentType() throws Exception {
        LegalAddDTO legalAddDTO = LegalAddDTO.builder()
                .countOfEmployees(1)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();
        LegalDTO legalDTO = LegalDTO.builder()
                .id(1L)
                .countOfEmployees(1)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();
        LegalAddResultDTO resultDTO = LegalAddResultDTO.builder()
                .message(MessageConstants.SUCCESS_ADD_MESSAGE)
                .legalDTO(legalDTO)
                .build();
        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalAddDTO)).thenReturn(null);
        when(addLegalService.addLegal(legalAddDTO)).thenReturn(legalDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalAddDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(resultDTO));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidContentType() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .build();

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .build();

        mockMvc.perform(get("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenNameIsNull() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(1)
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenIbanIsNull() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .name("Test")
                .countOfEmployees(1)
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenUnpIsNull() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .name("Test")
                .countOfEmployees(1)
                .iban("BY86UNBS10100000002966000229")
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenIbanIsInvalid() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .name("Test")
                .countOfEmployees(1)
                .iban("BY86UNBS10100000002966")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenUnpIsInvalid() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .name("Test")
                .countOfEmployees(1)
                .iban("BY86UNBS10100000002966000229")
                .unp(123456)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenEmployeeCountIsZero() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(0)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenEmployeeCountIsThousandAndOne() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(1001)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void shouldCreateWhenEmployeeCountIsThousand() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(1000)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenLegalIsNotUnique() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(1000)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .residence(ResidenceEnum.RESIDENT)
                .build();

        LegalDTO resultLegal = LegalDTO.builder().build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(addLegalService.addLegal(legalDTO)).thenThrow(LegalNotUniqueException.class);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenEmployeeResidenceIsNull() throws Exception {
        LegalAddDTO legalDTO = LegalAddDTO.builder()
                .countOfEmployees(10)
                .name("Test")
                .iban("BY86UNBS10100000002966000229")
                .unp(123456789)
                .build();

        when(config.getMinCountEmployees()).thenReturn(0);
        when(config.getMaxCountEmployees()).thenReturn(1000);
        when(config.getIbanBynRegex()).thenReturn("^BY\\d{2}UNBS\\d{20}$");
        when(config.getUnpRegex()).thenReturn("^\\d{9}$");
        when(getLegalByUniqueFields.getLegalsByNotUniqueFields(legalDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/legals")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(legalDTO)))
                .andExpect(status().isBadRequest());
    }
}