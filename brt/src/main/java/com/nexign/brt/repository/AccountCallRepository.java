package com.nexign.brt.repository;

import com.nexign.brt.entity.AccountCall;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccountCallRepository extends CrudRepository<AccountCall, Long> {

    @Query(value =
      "select sum(EXTRACT(epoch FROM duration) / 60) " +
        "from account_call " +
        "where account_id = ?1 " +
        "and date_part('MONTH', start_date) = ?2 " +
        "and date_part('YEAR', start_date) = ?3", nativeQuery = true)
    Optional<Long> findByAccountIdAndDate(long accountId, int month, int year);

    List<AccountCall> findByAccountId(long accountId);
}
