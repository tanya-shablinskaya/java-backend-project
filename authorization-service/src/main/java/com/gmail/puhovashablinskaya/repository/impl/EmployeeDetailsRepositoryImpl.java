package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeDetailsRepository;
import com.gmail.puhovashablinskaya.repository.model.EmployeeDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.time.LocalDateTime;

@Repository
public class EmployeeDetailsRepositoryImpl extends GenericRepositoryImpl<Long, EmployeeDetails>
        implements EmployeeDetailsRepository {

    @Override
    public void add(EmployeeDetails entity) {
        super.add(entity);
    }

    @Override
    public void saveDateOfLogout(String username, LocalDateTime currentTime) {
        String queryString = "update EmployeeDetails as e set e.dateOfLogout=:dateOfLogout where e.username=:username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("dateOfLogout", currentTime);
        query.setParameter("username", username);
        query.executeUpdate();
    }
}

