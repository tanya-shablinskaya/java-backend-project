package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetLegalsByIdService;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegalGetByIdController {
    private final GetLegalsByIdService getLegalsByIdService;

    @GetMapping(value = "/api/v1/legals/{id}")
    public LegalDTO getLegalById(@PathVariable Long id) {
        return getLegalsByIdService.getLegalById(id);
    }
}
