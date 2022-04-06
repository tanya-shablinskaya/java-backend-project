package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.SessionRepository;
import com.gmail.puhovashablinskaya.repository.model.Session;
import com.gmail.puhovashablinskaya.repository.model.StatusSession;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl extends GenericRepositoryImpl<Long, Session>
        implements SessionRepository {

    @Override
    public Optional<Session> findSessionByIdSession(String sessionId) {
        String queryString = "select s from Session as s where s.idSession=:idSession";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("idSession", sessionId);
        try {
            Session session = (Session) query.getSingleResult();
            return Optional.ofNullable(session);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateStatusSessionById(String sessionID, StatusSession statusSession) {
        String queryString = "update Session as s set s.statusSession=:statusSession where s.idSession=:sessionID";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("statusSession", statusSession);
        query.setParameter("sessionID", sessionID);
        query.executeUpdate();
    }

    @Override
    public void closeAllSession(String username, StatusSession statusSession, LocalDateTime currentTime) {
        String queryString = "update Session as s set s.statusSession=:statusSession, s.dateOfFinish =: currentTime where s.username=:username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("statusSession", statusSession);
        query.setParameter("currentTime", currentTime);
        query.setParameter("username", username);
        query.executeUpdate();
    }

    @Override
    public List<Session> findAllSession(String username) {
        String queryString = "select s from Session as s where s.username=:username";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("username", username);
        try {
            return (List<Session>) query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }

    }

    @Override
    public boolean isActiveSession(String jwt, StatusSession statusActive) {
        String queryString = "select s from Session as s where s.idSession=:idSession and s.statusSession=:statusSession";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("idSession", jwt);
        query.setParameter("statusSession", statusActive);
        return query.getResultList().isEmpty();
    }

    @Override
    public String findUsernameByJwt(String jwt) {
        String queryString = "select s.username from Session as s where s.idSession=:idSession";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("idSession", jwt);
        return (String) query.getSingleResult();
    }
}
