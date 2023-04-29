package com.nexign.tariff.repository;

import com.nexign.tariff.entity.Tariff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TariffRepository extends CrudRepository<Tariff, Long > {

    @Query(
      value =
        "select cur.name " +
          "from currency cur " +
          "join tariff_call_type_cost ctc on cur.id = ctc.currency_id " +
          "join tariff_call_type ct on ctc.tariff_call_type_id = ct.id " +
          "where ct.tariff_id = ?1 " +
          "group by cur.name",
      nativeQuery = true
    )
    String findCurrencyByTariffId(long tariffId);
}
