package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EmployeeRepository extends GenericRepository<Long, Employee> {
    Page<Employee> findAll(PageRequest pageable);

    List<Employee> getByNameAndLegalId(String nameEmployee, List<Long> legalId);

    List<Employee> getByIbanBynOrIbanCurrency(String ibanByn, String ibanCurrency);
}
