package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.service.GetEmployeeService;
import com.gmail.puhovashablinskaya.service.converter.EmployeeConverter;
import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GetEmployeeServiceImpl implements GetEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter converter;
    private final static Integer PAGE_SHIFT = 1;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<EmployeeDTO> getEmployeesList(Integer countOfElementsPage, Integer page) {
        page = page - PAGE_SHIFT;
        PageRequest pageable = PageRequest.of(page, countOfElementsPage);
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.stream()
                .map(converter::convertModelToDTO)
                .collect(Collectors.toList());
    }
}
