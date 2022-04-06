package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByInfoService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.AddEmployeeService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.EmployeeUniqueException;
import com.gmail.puhovashablinskaya.service.exception.LegalException;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddEmployeeServiceImpl implements AddEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private final FindLegalsByInfoService findLegalsByInfoService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public EmployeeAddResultDTO addEmployee(EmployeeAddDTO employeeAddDTO) {
        validateUniqueEmployee(employeeAddDTO);

        Employee employee = employeeConverter.convertDtoToModel(employeeAddDTO);

        employee.setDateOfCreate(LocalDateTime.now());

        employeeRepository.add(employee);

        return EmployeeAddResultDTO.builder()
                .employee(employeeConverter.convertModelToDTO(employee))
                .message(MessageConstants.SUCCESS_ADD_EMPLOYEE)
                .build();
    }

    void validateUniqueEmployee(EmployeeAddDTO employeeAddDTO) {
        String legalName = employeeAddDTO.getLegalName();
        List<LegalDTO> legals;
        try {
            legals = findLegalsByInfoService.getLegalsByInfo(legalName, null);
        } catch (FeignException ex) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }
        List<Long> legalIdList = legals.stream()
                .map(LegalDTO::getId)
                .collect(Collectors.toList());

        List<Employee> employeesByNameAndLegalId = employeeRepository.getByNameAndLegalId(employeeAddDTO.getName(), legalIdList);
        if (!employeesByNameAndLegalId.isEmpty()) {
            throw new EmployeeUniqueException(MessageConstants.EMPLOYEE_NOT_UNIQUE);
        }

        List<Employee> employeesByIban = employeeRepository.getByIbanBynOrIbanCurrency(employeeAddDTO.getIbanByn(), employeeAddDTO.getIbanCurrency());
        if (!employeesByIban.isEmpty()) {
            throw new EmployeeUniqueException(MessageConstants.EMPLOYEE_NOT_UNIQUE);
        }
    }
}
