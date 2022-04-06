package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByIdService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.exception.EmployeeException;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdImplTest {
    @InjectMocks
    private GetEmployeeByIdImpl getEmployeeById;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeConverter converter;
    @Mock
    private FindLegalsByIdService findLegalsService;

    @Test
    void shouldReturnEmployeeById() {
        Employee employee = new Employee();
        employee.setName("One");
        employee.setLegalId(1L);

        LegalDTO legalDTO = LegalDTO.builder()
                .id(1L)
                .build();

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .name("One")
                .legalName("One")
                .build();

        when(converter.convertModelToDTO(employee)).thenReturn(employeeDTO);
        when(employeeRepository.findById(1L)).thenReturn(employee);
        when(findLegalsService.getLegalById(1L)).thenReturn(legalDTO);

        EmployeeDTO byId = getEmployeeById.getById(1L);
        Assertions.assertEquals(employeeDTO, byId);
    }


    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        Employee employee = new Employee();
        employee.setName("One");
        employee.setLegalId(1L);

        when(employeeRepository.findById(1L)).thenReturn(null);

        EmployeeException exception = Assertions.assertThrows(EmployeeException.class, () -> {
            getEmployeeById.getById(1L);
        });
        Assertions.assertEquals(MessageConstants.EMPLOYEE_DOES_NOT_EXIST, exception.getMessage());
    }
}