package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.Legal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface LegalRepository extends GenericRepository<Long, Legal> {
    Page<Legal> findAll(PageRequest pageable);

    List<Legal> findByLegalInfo(String name, String iban, String unp);

    Optional<Legal> getByNameUnpIban(Legal legal);

    Optional<Legal> findByName(String name);
}
