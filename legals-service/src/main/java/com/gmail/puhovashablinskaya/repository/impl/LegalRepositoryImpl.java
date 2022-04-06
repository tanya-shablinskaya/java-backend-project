package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.LegalRepository;
import com.gmail.puhovashablinskaya.repository.model.Legal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class LegalRepositoryImpl extends GenericRepositoryImpl<Long, Legal>
        implements LegalRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Page<Legal> findAll(PageRequest pageable) {
        Query query = em.createQuery("SELECT a FROM Legal a");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Legal> legals = query.getResultList();
        Query queryCount = em.createQuery("SELECT count(a) FROM Legal a");
        Long count = (Long) queryCount.getSingleResult();
        return new PageImpl<>(legals, pageable, count);
    }

    @Override
    public List<Legal> findByLegalInfo(String name, String iban, String unp) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Legal> query = builder.createQuery(Legal.class);
        Root<Legal> legalRoot = query.from(Legal.class);

        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(builder.like(legalRoot.get("name"), name + "%"));
        }
        if (unp != null) {
            predicates.add(builder.like(legalRoot.get("unp"), unp + "%"));
        }
        if (iban != null) {
            predicates.add(builder.like(legalRoot.get("iban"), iban + "%"));
        }
        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])))
                    .orderBy(builder.asc(legalRoot.get("unp")));
        }
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Legal> getByNameUnpIban(Legal legal) {
        Query query = em.createQuery("SELECT a FROM Legal a " +
                "WHERE a.name LIKE :name " +
                "OR a.unp LIKE :unp " +
                "OR a.iban LIKE :iban");

        query.setParameter("name", legal.getName());
        query.setParameter("unp", legal.getUnp());
        query.setParameter("iban", legal.getIban());
        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Legal> findByName(String name) {
        Query query = em.createQuery("SELECT a FROM Legal a " +
                "WHERE a.name LIKE :name");

        query.setParameter("name", name);
        return query.getResultList()
                .stream()
                .findFirst();
    }
}
