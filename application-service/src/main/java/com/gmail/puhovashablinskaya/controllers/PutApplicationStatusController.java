package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.PutApplicationStatusService;
import com.gmail.puhovashablinskaya.service.model.MessageDTO;
import com.gmail.puhovashablinskaya.service.model.StatusApplicationEnumDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PutApplicationStatusController {
    private final PutApplicationStatusService putApplicationStatusService;

    @PutMapping(value = "/applications")
    public MessageDTO putApplicationStatusById(@RequestParam("applicationConvId") Long id,
                                               @RequestParam(name = "status") StatusApplicationEnumDTO status,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        return putApplicationStatusService.putApplicationStatusById(id, status, jwt);
    }
}
