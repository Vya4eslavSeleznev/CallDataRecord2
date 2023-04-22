package com.nexign.brt.repository;

import com.nexign.brt.entity.AccountCall;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface AccountCallRepository extends CrudRepository<AccountCall, Long> {

    //@Query(value = "select sum(duration) from account_call where account_id = ?1 and start_date >= ?2 and end_date < ?3", nativeQuery = true)
    //int findByAccountIdAndDate(long accountId, Date startDate, Date endDate);
}
