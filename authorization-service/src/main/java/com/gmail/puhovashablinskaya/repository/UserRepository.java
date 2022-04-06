package com.gmail.puhovashablinskaya.repository;


import com.gmail.puhovashablinskaya.repository.model.Employee;

import java.util.Optional;

public interface UserRepository extends GenericRepository<Long, Employee> {

    Optional<Employee> findByUsername(String username);

    Optional<Employee> findUserByUsermail(String login);

    Optional<Employee> findUserByUsermailOrUsername(String login);

    boolean isUsernameUnique(String username);

    boolean isUsermailUnique(String usermail);

    void updateFailedAttempts(Integer failAttempts, String username);
}
