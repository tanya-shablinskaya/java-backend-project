package com.gmail.puhovashablinskaya.repository.impl;

import com.gmail.puhovashablinskaya.repository.EmployeeRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
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

@Repository
@Slf4j
public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Long, Employee>
        implements EmployeeRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Page<Employee> findAll(PageRequest pageable) {
        Query query = em.createQuery("SELECT e FROM Employee e");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Employee> employees = query.getResultList();
        Query queryCount = em.createQuery("SELECT count(e) FROM Employee e");
        Long count = (Long) queryCount.getSingleResult();
        return new PageImpl<>(employees, pageable, count);
    }

    @Override
    public List<Employee> getByNameAndLegalId(String nameEmployee, List<Long> legalId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> employeeRoot = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();
        if (legalId != null) {
            predicates.add((employeeRoot.get("legalId").in(legalId)));
        }
        if (nameEmployee != null) {
            predicates.add(builder.like(employeeRoot.get("name"), "%" + nameEmployee + "%"));
        }
        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Employee> getByIbanBynOrIbanCurrency(String ibanByn, String ibanCurrency) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> employeeRoot = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();
        if (ibanByn != null) {
            predicates.add(builder.like(employeeRoot.get("ibanByn"), ibanByn));
        }
        if (ibanCurrency != null) {
            predicates.add(builder.like(employeeRoot.get("ibanCurrency"), ibanCurrency));
        }
        if (!predicates.isEmpty()) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }
        return em.createQuery(query).getResultList();
    }
}
