package com.gmail.puhovashablinskaya.repository.feign.request;

import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "legalsService", url = "http://localhost:9002/")
public interface FindLegalByNameService {

    @GetMapping(value = "/api/v1/legals")
    List<LegalDTO> getLegalsByInfo(@RequestParam(name = "name", required = false) String name);

}
