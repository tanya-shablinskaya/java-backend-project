package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.StatusSessionRepository;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import com.gmail.puhovashablinskaya.repository.model.StatusSessionEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class StatusSessionRepositoryImpl extends GenericRepositoryImpl<Long, StatusSession>
        implements StatusSessionRepository {

    @Override
    public Optional<StatusSession> findByName(StatusSessionEnum name) {
        String queryString = "select s from StatusSession as s where s.name=:name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        StatusSession status;
        try {
            status = (StatusSession) query.getSingleResult();
            return Optional.ofNullable(status);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
