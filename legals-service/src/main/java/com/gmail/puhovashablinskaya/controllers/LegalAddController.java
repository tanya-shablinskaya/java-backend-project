package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.AddLegalService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.LegalAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LegalAddController {
    private final AddLegalService addLegalService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/v1/legals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LegalAddResultDTO addLegal(@RequestBody @Validated LegalAddDTO legalDTO) {
        LegalDTO resultLegalDTO = addLegalService.addLegal(legalDTO);
        return LegalAddResultDTO.builder()
                .legalDTO(resultLegalDTO)
                .message(MessageConstants.SUCCESS_ADD_MESSAGE)
                .build();
    }
}
