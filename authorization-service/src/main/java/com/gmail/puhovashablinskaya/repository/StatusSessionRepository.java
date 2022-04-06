package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import com.gmail.puhovashablinskaya.repository.model.StatusSessionEnum;

import java.util.Optional;

public interface StatusSessionRepository extends GenericRepository<Long, StatusSession> {
    Optional<StatusSession> findByName(StatusSessionEnum name);
}
