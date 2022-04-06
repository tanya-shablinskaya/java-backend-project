package com.gmail.puhovashablinskaya.service.convert;

import com.gmail.puhovashablinskaya.repository.model.EmployeeDetails;
import com.gmail.puhovashablinskaya.service.model.EmployeeDetailsDTO;

public interface ConvertEmployeeDetails {

    EmployeeDetails convertToEmployeeDetails(EmployeeDetailsDTO employeeDetailsDTO);

    EmployeeDetailsDTO convertToDTO(EmployeeDetails employeeDetails);
}
