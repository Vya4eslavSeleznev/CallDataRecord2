package com.nexign.brt.repository;

import com.nexign.brt.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByUserId(long userId);
}
