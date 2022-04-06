package com.gmail.puhovashablinskaya.repository.feign.request;

import com.gmail.puhovashablinskaya.service.model.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employeesService", url = "http://localhost:9003/")
public interface FindEmployeeInfoService {
    @GetMapping(value = "/api/v1/employees/{employeeId}")
    EmployeeDTO getEmployeeById(@PathVariable Long employeeId);
}
