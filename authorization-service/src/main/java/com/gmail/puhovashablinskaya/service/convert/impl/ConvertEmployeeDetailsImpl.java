package com.gmail.puhovashablinskaya.service.convert.impl;

import com.gmail.puhovashablinskaya.repository.model.EmployeeDetails;
import com.gmail.puhovashablinskaya.service.convert.ConvertEmployeeDetails;
import com.gmail.puhovashablinskaya.service.model.EmployeeDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ConvertEmployeeDetailsImpl implements ConvertEmployeeDetails {
    @Override
    public EmployeeDetails convertToEmployeeDetails(EmployeeDetailsDTO employeeDetailsDTO) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setUsername(employeeDetailsDTO.getUsername());
        employeeDetails.setDateOfAuth(employeeDetailsDTO.getDateOfAuth());
        employeeDetails.setDateOfLogout(employeeDetailsDTO.getDateOfLogout());
        return employeeDetails;
    }

    @Override
    public EmployeeDetailsDTO convertToDTO(EmployeeDetails employeeDetails) {
        return EmployeeDetailsDTO.builder()
                .username(employeeDetails.getUsername())
                .dateOfAuth(employeeDetails.getDateOfAuth())
                .dateOfLogout(employeeDetails.getDateOfLogout())
                .build();
    }
}
