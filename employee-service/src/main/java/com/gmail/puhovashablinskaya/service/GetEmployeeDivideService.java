package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.PaginationDTO;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;

import java.util.List;

public interface GetEmployeeDivideService {
    List<EmployeeDTO> getEmployees(PaginationDTO pagination, SearchEmployeeDTO searchEmployeeDTO);
}
