package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByInfoService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.FindEmployeesService;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.EmployeeException;
import com.gmail.puhovashablinskaya.service.exception.LegalException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FindEmployeesServiceImpl implements FindEmployeesService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter converter;
    private final FindLegalsByInfoService findLegalsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<EmployeeDTO> findEmployees(SearchEmployeeDTO searchEmployeeDTO) {
        String employeeName = searchEmployeeDTO.getEmployeeName();
        String unp = searchEmployeeDTO.getUnp();
        String legalName = searchEmployeeDTO.getLegalName();
        List<LegalDTO> legalsByInfo;
        try {
            legalsByInfo = findLegalsService.getLegalsByInfo(legalName, unp);
        } catch (FeignException ex) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }

        List<Long> legalIdList = legalsByInfo.stream()
                .map(LegalDTO::getId).
                collect(Collectors.toList());

        List<Employee> employees = employeeRepository.getByNameAndLegalId(employeeName, legalIdList);
        if (employees.isEmpty()) {
            throw new EmployeeException(MessageConstants.EMPLOYEE_SEARCH_ERROR);
        }
        return employees.stream()
                .map(converter::convertModelToDTO)
                .collect(Collectors.toList());
    }
}
