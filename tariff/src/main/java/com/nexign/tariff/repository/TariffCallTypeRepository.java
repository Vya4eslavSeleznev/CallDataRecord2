package com.nexign.tariff.repository;

import com.nexign.common.model.CallType;
import com.nexign.tariff.entity.TariffCallType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TariffCallTypeRepository extends CrudRepository<TariffCallType, Long> {

    List<TariffCallType> findByTariffId(long tariffId);
    TariffCallType findByTariffIdAndCallType(long tariffId, CallType callType);
}
