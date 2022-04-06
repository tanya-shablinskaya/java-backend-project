package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByIdService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.GetEmployeeByIdService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.EmployeeException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class GetEmployeeByIdImpl implements GetEmployeeByIdService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter converter;
    private final FindLegalsByIdService findLegalsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public EmployeeDTO getById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee != null) {
            Long legalId = employee.getLegalId();
            LegalDTO legal = findLegalsService.getLegalById(legalId);

            String legalName = legal.getName();
            EmployeeDTO employeeDTO = converter.convertModelToDTO(employee);
            employeeDTO.toBuilder().legalName(legalName).build();
            return employeeDTO;
        }
        throw new EmployeeException(MessageConstants.EMPLOYEE_DOES_NOT_EXIST);
    }
}
