package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.StatusRepository;
import com.gmail.puhovashablinskaya.repository.model.Status;
import com.gmail.puhovashablinskaya.repository.model.StatusEmployeeEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class StatusRepositoryImpl extends GenericRepositoryImpl<Long, Status>
        implements StatusRepository {

    @Override
    public Optional<Status> findByName(StatusEmployeeEnum name) {
        String queryString = "select s from Status as s where s.name=:name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        Status status;
        try {
            status = (Status) query.getSingleResult();
            return Optional.ofNullable(status);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
