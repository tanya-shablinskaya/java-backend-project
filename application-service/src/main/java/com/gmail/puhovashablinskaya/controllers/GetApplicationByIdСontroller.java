package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetApplicationByIdService;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class GetApplicationByIdСontroller {
    private final GetApplicationByIdService getApplicationByIdService;

    @GetMapping(path = "/applications/{id}")
    public ApplicationDTO getApplicationById(@PathVariable("id") Long id) {
        return getApplicationByIdService.getApplicationById(id);
    }
}
