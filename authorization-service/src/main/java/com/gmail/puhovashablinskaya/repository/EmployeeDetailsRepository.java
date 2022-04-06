package com.gmail.puhovashablinskaya.repository;


import com.gmail.puhovashablinskaya.repository.model.EmployeeDetails;

import java.time.LocalDateTime;

public interface EmployeeDetailsRepository extends GenericRepository<Long, EmployeeDetails> {

    void saveDateOfLogout(String username, LocalDateTime currentTime);
}
