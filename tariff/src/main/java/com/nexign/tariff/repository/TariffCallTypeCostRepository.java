package com.nexign.tariff.repository;

import com.nexign.tariff.entity.TariffCallTypeCost;
import org.springframework.data.repository.CrudRepository;

public interface TariffCallTypeCostRepository extends CrudRepository<TariffCallTypeCost, Long> {

    TariffCallTypeCost findByTariffCallTypeId(long tariffCallTypeId);
}
