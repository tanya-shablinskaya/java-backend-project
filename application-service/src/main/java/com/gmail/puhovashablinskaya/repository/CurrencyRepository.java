package com.gmail.puhovashablinskaya.repository;

import com.gmail.puhovashablinskaya.repository.model.Currency;
import com.gmail.puhovashablinskaya.repository.model.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findCurrencyByName(CurrencyEnum name);

}
