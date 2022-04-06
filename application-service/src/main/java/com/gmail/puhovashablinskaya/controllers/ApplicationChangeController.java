package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.PutApplicationLegalNameService;
import com.gmail.puhovashablinskaya.service.model.ApplicationChangeResultDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ApplicationChangeController {
    private PutApplicationLegalNameService putApplicationLegalNameService;

    @PutMapping(value = "/applications/{id}")
    public ApplicationChangeResultDTO putApplicationById(@PathVariable("id") Long id,
                                                         @RequestParam(name = "Name_Legal") String legalName) {
        return putApplicationLegalNameService.putApplicationLegalNameById(id, legalName);
    }
}

