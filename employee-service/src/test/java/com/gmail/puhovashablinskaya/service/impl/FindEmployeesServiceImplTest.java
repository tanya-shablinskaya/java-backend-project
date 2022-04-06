package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByInfoService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.EmployeeException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import com.gmail.puhovashablinskaya.service.model.SearchEmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindEmployeesServiceImplTest {
    @InjectMocks
    private FindEmployeesServiceImpl findEmployeesService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeConverter converter;
    @Mock
    private FindLegalsByInfoService findLegalsService;

    @Test
    void shouldReturnListOfEmployeesWhenEmployeeIsFound() {
        SearchEmployeeDTO searchEmployeeDTO = SearchEmployeeDTO.builder()
                .employeeName("Имя")
                .unp("111")
                .legalName("11")
                .build();

        LegalDTO legalDTO = LegalDTO.builder()
                .name("Имя")
                .id(1L)
                .build();

        Employee employee = new Employee();
        employee.setLegalId(1L);
        employee.setName("Имя");

        List<LegalDTO> legalsByInfo = List.of(legalDTO);
        when(findLegalsService.getLegalsByInfo("11", "111")).thenReturn(legalsByInfo);
        when(employeeRepository.getByNameAndLegalId("Имя", List.of(1L))).thenReturn(List.of(employee));


        List<EmployeeDTO> employees = findEmployeesService.findEmployees(searchEmployeeDTO);
        Assertions.assertFalse(employees.isEmpty());
    }


    @Test
    void shouldReturnErrorWhenEmployeeIsNotFound() {
        SearchEmployeeDTO searchEmployeeDTO = SearchEmployeeDTO.builder()
                .employeeName("Имя")
                .unp("111")
                .legalName("11")
                .build();

        LegalDTO legalDTO = LegalDTO.builder()
                .name("Имя")
                .id(1L)
                .build();

        List<LegalDTO> legalsByInfo = List.of(legalDTO);
        when(findLegalsService.getLegalsByInfo("11", "111")).thenReturn(legalsByInfo);
        when(employeeRepository.getByNameAndLegalId("Имя", List.of(1L))).thenReturn(List.of());

        EmployeeException exception = Assertions.assertThrows(EmployeeException.class, () -> {
            findEmployeesService.findEmployees(searchEmployeeDTO);
        });
        Assertions.assertEquals(MessageConstants.EMPLOYEE_SEARCH_ERROR, exception.getMessage());
    }
}