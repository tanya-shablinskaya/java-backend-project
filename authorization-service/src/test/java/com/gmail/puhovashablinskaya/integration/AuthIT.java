package com.gmail.puhovashablinskaya.integration;

import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.controller.security.model.LogoutRequest;
import com.gmail.puhovashablinskaya.controller.security.util.JwtUtils;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthIT extends BaseIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void addUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        UserAddDTO userAddDTO = findUserValid();

        HttpEntity<String> entity = new HttpEntity(userAddDTO, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/signin"),
                HttpMethod.POST,
                entity,
                Object.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    @Sql({"/sql/userAddAuth.sql"})
    void loginUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        AuthRequest authRequest = validUserCredential();

        HttpEntity<String> entity = new HttpEntity(authRequest, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/login"),
                HttpMethod.POST,
                entity,
                Object.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }


    @Test
    @Sql({"/sql/sessionQuery.sql"})
    void sessionIT() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYWFhYWFhYWFhYSIsInVzZXJJZCI6NiwiY2hlY2tVc2VySWQiOnRydWUsImlhdCI6MTY0ODM4NTk1MiwiZXhwIjoxNjk4OTc1MTkyfQ.bMK1yNXx-OXHbwW2zzURLlwjUf-l0Wya7Kd4DrSsN-DC97VgvhQdP0YO2BtcgeZ-votX4cwzar7--xp4qd_tYg";

        HttpEntity<String> entity = new HttpEntity(jwt, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/sessions"),
                HttpMethod.POST,
                entity,
                Object.class
        );
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    @Sql({"/sql/userLogout.sql"})
    void logoutUser() {
        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWFhYWFhYWFhIiwidXNlcklkIjoyLCJjaGVja1VzZXJJZCI6dHJ1ZSwiaWF0IjoxNjQ4NDAxNDI4LCJleHAiOjE3MTE1NDg5NzN9.gAdccA3HvRHwBexaSVpanRWj_5dgBN3ytMac06P4yPm9jXg1lnoHg-5WYzeeb17aYnnaUTw_lgXbpeHac7lA4g";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        LogoutRequest logoutRequest = userAccountAndSession();

        HttpEntity<String> entity = new HttpEntity(logoutRequest, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                createURLWithPort("/api/v1/auth/logout"),
                HttpMethod.POST,
                entity,
                Object.class
        );
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    private UserAddDTO findUserValid() {
        return UserAddDTO.builder()
                .username("username")
                .password("password")
                .usermail("viexdg@gmail.com")
                .firstName("Бимбус")
                .build();
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private AuthRequest validUserCredential() {
        return new AuthRequest("bimbusyes", "qqqqqqqqqq");
    }

    private LogoutRequest userAccountAndSession() {
        return new LogoutRequest("aaaaaaaaaa", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYWFhYWFhYWFhIiwidXNlcklkIjoyLCJjaGVja1VzZXJJZCI6dHJ1ZSwiaWF0IjoxNjQ4NDAxNDI4LCJleHAiOjE3MTE1NDg5NzN9.gAdccA3HvRHwBexaSVpanRWj_5dgBN3ytMac06P4yPm9jXg1lnoHg-5WYzeeb17aYnnaUTw_lgXbpeHac7lA4g");
    }

}