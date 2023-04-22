package com.nexign.tariff.repository;

import com.nexign.tariff.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
