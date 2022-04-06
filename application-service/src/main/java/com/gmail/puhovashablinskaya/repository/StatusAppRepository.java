package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.StatusApplication;
import com.gmail.puhovashablinskaya.repository.model.StatusApplicationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusAppRepository extends JpaRepository<StatusApplication, Long> {
    Optional<StatusApplication> getStatusApplicationByName(StatusApplicationEnum name);

}
