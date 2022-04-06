package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.feign.request.FindLegalsByInfoService;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddResultDTO;
import com.gmail.puhovashablinskaya.service.model.LegalDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddEmployeeServiceImplTest {
    @InjectMocks
    private AddEmployeeServiceImpl addEmployeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeConverter employeeConverter;
    @Mock
    private FindLegalsByInfoService findLegalsByInfoService;

    @Test
    void createNewEmployeeTest() {
        EmployeeAddDTO employeeDTO = EmployeeAddDTO.builder()
                .name("Иванов")
                .ibanByn("BY45UNBS30122B32500010270933")
                .ibanCurrency("BY45ALFA30122B32500010270840")
                .recruitmentDate(LocalDate.of(2019, 12, 1))
                .legalName("Company")
                .terminationDate(LocalDate.of(2044, 12, 1))
                .build();
        LegalDTO legal = LegalDTO.builder().name("Company").id(1L).build();
        List<LegalDTO> legals = List.of(legal);

        Employee employee = new Employee();
        employee.setName("Иванов");
        employee.setRecruitmentDate(LocalDate.of(2019, 12, 1));
        employee.setTerminationDate(LocalDate.of(2044, 12, 1));
        employee.setIbanByn("BY45UNBS30122B32500010270933");
        employee.setIbanCurrency("BY45ALFA30122B32500010270840");

        when(findLegalsByInfoService.getLegalsByInfo("Company", null)).thenReturn(legals);
        when(employeeConverter.convertDtoToModel(employeeDTO)).thenReturn(employee);
        EmployeeAddResultDTO employeeAddResultDTO = addEmployeeService.addEmployee(employeeDTO);
        Assertions.assertEquals("Сотрудник успешно создан", employeeAddResultDTO.getMessage());
    }

}