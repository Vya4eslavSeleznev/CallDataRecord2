package com.nexign.tariff.repository;

import com.nexign.tariff.entity.Tariff;
import org.springframework.data.repository.CrudRepository;

public interface TariffRepository extends CrudRepository<Tariff, Long > {
}
