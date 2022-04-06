package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;

import java.util.List;

public interface GetEmployeeService {
    List<EmployeeDTO> getEmployeesList(Integer countOfElementsPage, Integer page);
}
