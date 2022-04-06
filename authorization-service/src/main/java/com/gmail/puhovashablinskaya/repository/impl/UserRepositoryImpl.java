package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.UserRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, Employee>
        implements UserRepository {

    @Override
    public Optional<Employee> findByUsername(String username) {
        String queryString = "select e from Employee as e where e.username=:username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("username", username);
        try {
            Employee user = (Employee) query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findUserByUsermailOrUsername(String login) {
        String queryString = "select e from Employee as e where e.usermail=:login or e.username=:login";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("login", login);
        try {
            Employee user = (Employee) query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> findUserByUsermail(String login) {
        String queryString = "select e from Employee as e where e.usermail=:login";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("login", login);
        try {
            Employee user = (Employee) query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isUsernameUnique(String username) {
        String queryString = "select e from Employee as e where e.username = :username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("username", username);
        return query.getResultList().isEmpty();
    }

    @Override
    public boolean isUsermailUnique(String usermail) {
        String queryString = "select e from Employee as e where e.usermail = :usermail";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("usermail", usermail);
        return query.getResultList().isEmpty();
    }

    @Override
    public void updateFailedAttempts(Integer failAttempts, String username) {
        String queryString = "update Employee as e set e.failedAttempt=:failedAttempt where e.username=:username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("failedAttempt", failAttempts);
        query.setParameter("username", username);
        query.executeUpdate();
    }
}
