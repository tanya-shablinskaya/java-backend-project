package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetEmployeeDivideService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.exception.PaginationException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationEnum;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class EmployeesGetController {
    private final GetEmployeeDivideService getEmployeeDivide;

    @GetMapping(value = "/api/v1/employees")
    public List<EmployeeDTO> getLegalsList(@RequestParam(name = "pagination", required = false) String pagination,
                                           @RequestParam(name = "customized_page", required = false) Integer elementCount,
                                           @RequestParam(name = "page", required = false) Integer page,
                                           @RequestParam(name = "Name_Legal", required = false) String legalName,
                                           @RequestParam(name = "UNP", required = false) String unp,
                                           @RequestParam(name = "Full_Name_Individual", required = false) String employeeName) {
        if (pagination != null
                && legalName != null
                || unp != null
                || employeeName != null) {
            throw new PaginationException(MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE);
        }

        PaginationDTO paginationDTO = PaginationDTO.builder()
                .pagination(getPaginationValue(pagination))
                .customizedPage(elementCount)
                .page(page)
                .build();

        SearchEmployeeDTO search = SearchEmployeeDTO.builder()
                .legalName(legalName)
                .unp(unp)
                .employeeName(employeeName)
                .build();

        return getEmployeeDivide.getEmployees(paginationDTO, search);
    }

    private PaginationEnum getPaginationValue(String pagination) {
        if (pagination != null) {
            return PaginationEnum.valueOf(pagination.toUpperCase());
        }
        return null;
    }
}
