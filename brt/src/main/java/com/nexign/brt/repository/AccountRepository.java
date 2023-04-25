package com.nexign.brt.repository;

import com.nexign.brt.entity.Account;
import com.nexign.brt.model.UserBalanceInterface;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByUserId(long userId);
    List<UserBalanceInterface> findAllBy();
}
