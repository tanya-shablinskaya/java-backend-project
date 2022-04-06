package com.gmail.puhovashablinskaya.service;

import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;

public interface GetEmployeeByIdService {
    EmployeeDTO getById(Long employeeId);
}
