package com.nexign.tariff.repository;

import com.nexign.tariff.entity.TariffCallTypeCost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TariffCallTypeCostRepository extends CrudRepository<TariffCallTypeCost, Long> {

    List<TariffCallTypeCost> findByTariffCallTypeId(long tariffCallTypeId);
}
