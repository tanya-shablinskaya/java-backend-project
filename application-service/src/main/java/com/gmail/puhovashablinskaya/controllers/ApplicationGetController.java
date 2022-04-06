package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetApplicationsFactoryService;
import com.gmail.puhovashablinskaya.service.exceptions.PaginationException;
import com.gmail.puhovashablinskaya.service.model.ApplicationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.util.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ApplicationGetController {
    private final GetApplicationsFactoryService getApplicationService;

    @GetMapping(value = "/api/v1/applications")
    public List<ApplicationDTO> getLegalsList(@RequestParam(name = "pagination") String pagination,
                                              @RequestParam(name = "customized_page", required = false) Integer elementCount,
                                              @RequestParam(name = "page", required = false) Integer page) {
        PaginationDTO paginationDTO = PaginationDTO.builder()
                .pagination(getPaginationValue(pagination))
                .customizedPage(elementCount)
                .page(page)
                .build();


        return getApplicationService.getApplications(paginationDTO);
    }

    private PaginationEnum getPaginationValue(String pagination) {
        if (pagination == null) {
            throw new PaginationException(MessageConstants.PAGINATION_ERROR_MESSAGE);
        }
        return PaginationEnum.valueOf(pagination.toUpperCase());
    }
}
