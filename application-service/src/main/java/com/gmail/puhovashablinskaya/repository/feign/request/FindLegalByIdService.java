package com.gmail.puhovashablinskaya.repository.feign.request;

import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "legalsService", url = "http://localhost:9002/")
public interface FindLegalByIdService {

    @GetMapping(value = "/api/v1/legals/{id}")
    LegalDTO getLegalById(@PathVariable Long id);
}
