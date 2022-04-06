package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeDetailsRepository;
import com.gmail.puhovashablinskaya.repository.model.EmployeeDetails;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.EmployeeDetailsService;
import com.gmail.puhovashablinskaya.service.convert.ConvertEmployeeDetails;
import com.gmail.puhovashablinskaya.service.model.EmployeeDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {
    private final DataTimeService dataTimeService;
    private final EmployeeDetailsRepository detailsRepository;
    private final ConvertEmployeeDetails convertDetails;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void saveDateOfAuth(String username) {
        LocalDateTime currentTimeDate = dataTimeService.currentTimeDate();
        EmployeeDetailsDTO employeeDetailsDTO = EmployeeDetailsDTO.builder()
                .username(username)
                .dateOfAuth(currentTimeDate)
                .build();
        EmployeeDetails employeeDetails = convertDetails.convertToEmployeeDetails(employeeDetailsDTO);
        detailsRepository.add(employeeDetails);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void saveDateOfLogout(String username) {
        LocalDateTime currentTimeDate = dataTimeService.currentTimeDate();
        detailsRepository.saveDateOfLogout(username, currentTimeDate);
    }
}
