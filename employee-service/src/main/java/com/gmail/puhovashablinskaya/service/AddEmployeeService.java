package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddResultDTO;

public interface AddEmployeeService {
    EmployeeAddResultDTO addEmployee(EmployeeAddDTO employeeAddDTO);
}
