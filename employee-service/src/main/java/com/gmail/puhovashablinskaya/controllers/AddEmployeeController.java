package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.AddEmployeeService;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddResultDTO;
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
public class AddEmployeeController {
    private final AddEmployeeService addEmployeeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/v1/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeAddResultDTO addLegal(@RequestBody @Validated EmployeeAddDTO employeeDTO) {
        return addEmployeeService.addEmployee(employeeDTO);
    }
}
