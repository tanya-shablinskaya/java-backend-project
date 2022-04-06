package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetLegalsFactory;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.exceptions.PaginationException;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.model.SearchLegalDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class LegalsGetController {
    private final GetLegalsFactory getLegalsFactory;

    @GetMapping(value = "/api/v1/legals")
    public List<LegalDTO> getLegalsList(@RequestParam(name = "pagination", required = false) String pagination,
                                        @RequestParam(name = "customized_page", required = false) Integer elementCount,
                                        @RequestParam(name = "page", required = false) Integer page,
                                        @RequestParam(name = "name", required = false) String legalName,
                                        @RequestParam(name = "unp", required = false) String unp,
                                        @RequestParam(name = "IBANbyBYN", required = false) String iban) {

        if (pagination != null
                && (legalName != null
                || unp != null
                || iban != null)) {
            throw new PaginationException(MessageConstants.INVALID_INPUT_ERROR);
        }

        PaginationDTO paginationDTO = PaginationDTO.builder()
                .pagination(getPaginationValue(pagination))
                .customizedPage(elementCount)
                .page(page)
                .build();

        SearchLegalDTO searchDTO = SearchLegalDTO.builder()
                .name(legalName)
                .unp(unp)
                .iban(iban)
                .build();

        return getLegalsFactory.getLegals(paginationDTO, searchDTO);
    }

    private PaginationEnum getPaginationValue(String pagination) {
        if (pagination != null) {
            return PaginationEnum.valueOf(pagination.toUpperCase());
        }
        return null;
    }
}
