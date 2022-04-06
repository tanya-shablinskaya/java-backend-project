package com.gmail.puhovashablinskaya.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.puhovashablinskaya.feign.AuthCheckService;
import com.gmail.puhovashablinskaya.security.point.AuthEntryPointJwt;
import com.gmail.puhovashablinskaya.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.FileService;
import com.gmail.puhovashablinskaya.util.DateTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FileUploadController.class)
class FileUploadControllerTest {
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
    private FileService fileService;

    @Test
    @WithMockUser
    void shouldReturnSuccessWhenValidValue() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "1,2".getBytes()
        );

        mvc.perform(multipart("/api/v1/files")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void shouldReturnErrorWhenInvalidMethod() throws Exception {
        mvc.perform(get("/api/v1/files"))
                .andExpect(status().isMethodNotAllowed());
    }
}