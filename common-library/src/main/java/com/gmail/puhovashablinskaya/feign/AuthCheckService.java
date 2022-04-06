package com.gmail.puhovashablinskaya.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authorizationService", url = "http://localhost:9001/")
public interface AuthCheckService {
    @PostMapping(path = "/api/v1/auth/sessions", consumes = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> checkStatusJwt(@RequestBody String jwt);
}
