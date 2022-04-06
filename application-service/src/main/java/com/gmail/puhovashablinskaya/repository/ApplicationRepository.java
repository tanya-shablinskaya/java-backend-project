package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.Application;
import com.gmail.puhovashablinskaya.repository.model.StatusApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Modifying
    @Query("update Application a set a.legalId = :legalId, a.lastUpdateDate = :lastUpdateDate where a.id = :id")
    int updateApplicationSetLegalIdAndUpdateDateById(@Param("id") Long id,
                                                     @Param("legalId") Long legalId,
                                                     @Param("lastUpdateDate") LocalDateTime lastUpdateDate);

    @Modifying
    @Query("update Application a set a.status = :status where a.id = :id")
    int updateApplicationStatusById(@Param("id") Long id,
                                    @Param("status") StatusApplication status);

    Optional<Application> findApplicationByIdAndLegalId(Long id, Long legalId);

    @Override
    Application getById(Long id);

    @Override
    Page<Application> findAll(Pageable pageable);

    Application getApplicationByIdAndStatus(Long id, StatusApplication status);

    Optional<Application> getApplicationByApplicationIdAndStatus(Long applicationId, Long statusId);
}
