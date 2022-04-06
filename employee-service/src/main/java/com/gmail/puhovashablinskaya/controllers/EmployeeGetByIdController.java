package com.gmail.puhovashablinskaya.controllers;

import com.gmail.puhovashablinskaya.service.GetEmployeeByIdService;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeGetByIdController {
    private final GetEmployeeByIdService getEmployeeService;

    @GetMapping(value = "/api/v1/employees/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return getEmployeeService.getById(employeeId);
    }
}
