package com.gmail.puhovashablinskaya.service.converter.impl;

import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByIdService;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByInfoService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.LegalException;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeConverterImpl implements EmployeeConverter {
    private final FindLegalsByInfoService findLegalsByInfoService;
    private final FindLegalsByIdService findLegalsByIdService;

    @Override
    public EmployeeDTO convertModelToDTO(Employee employee) {
        EmployeeDTO.EmployeeDTOBuilder employeeDTOBuilder = EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .ibanByn(employee.getIbanByn())
                .ibanCurrency(employee.getIbanCurrency())
                .recruitmentDate(employee.getRecruitmentDate())
                .terminationDate(employee.getTerminationDate());

        LegalDTO legal = findLegalsByIdService.getLegalById(employee.getLegalId());
        employeeDTOBuilder.legalName(legal.getName());

        return employeeDTOBuilder.build();
    }

    @Override
    public Employee convertDtoToModel(EmployeeAddDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setRecruitmentDate(employeeDTO.getRecruitmentDate());
        employee.setTerminationDate(employeeDTO.getTerminationDate());
        employee.setIbanByn(employeeDTO.getIbanByn());
        employee.setIbanCurrency(employeeDTO.getIbanCurrency());

        String legalName = employeeDTO.getLegalName();
        List<LegalDTO> legals;
        try {
            legals = findLegalsByInfoService.getLegalsByInfo(legalName, null);
        } catch (FeignException ex) {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }

        Optional<LegalDTO> legal = legals.stream()
                .filter(legalDTO -> legalDTO.getName().equals(legalName))
                .findFirst();
        if (legal.isPresent()) {
            employee.setLegalId(legal.get().getId());
        } else {
            throw new LegalException(MessageConstants.LEGAL_NOT_FOUND_EXCEPTION);
        }

        return employee;
    }
}
