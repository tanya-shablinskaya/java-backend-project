package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.Status;
import com.gmail.puhovashablinskaya.repository.model.StatusEmployeeEnum;

import java.util.Optional;

public interface StatusRepository extends GenericRepository<Long, Status> {
    Optional<Status> findByName(StatusEmployeeEnum name);
}
