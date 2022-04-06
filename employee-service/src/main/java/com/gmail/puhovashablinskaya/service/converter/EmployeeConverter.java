package com.gmail.puhovashablinskaya.service.converter;

import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;

public interface EmployeeConverter {
    EmployeeDTO convertModelToDTO(Employee employee);

    Employee convertDtoToModel(EmployeeAddDTO employeeDTO);

}
