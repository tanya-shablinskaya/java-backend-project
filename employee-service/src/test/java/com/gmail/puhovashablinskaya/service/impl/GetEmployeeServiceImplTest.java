package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeeServiceImplTest {
    @InjectMocks
    private GetEmployeeServiceImpl getEmployeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeConverter converter;

    @Test
    void shouldGetEmployeeEmpty() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Employee> page = Page.empty(pageable);
        when(employeeRepository.findAll(pageable)).thenReturn(page);
        List<EmployeeDTO> applicationsList = getEmployeeService.getEmployeesList(10, 1);
        Assertions.assertTrue(applicationsList.isEmpty());
    }

}