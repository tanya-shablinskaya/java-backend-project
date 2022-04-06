package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;

import java.util.List;

public interface FindEmployeesService {
    List<EmployeeDTO> findEmployees(SearchEmployeeDTO searchEmployeeDTO);
}
