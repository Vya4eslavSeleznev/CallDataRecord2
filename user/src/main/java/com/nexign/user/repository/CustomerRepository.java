package com.nexign.user.repository;

import com.nexign.user.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByPhoneNumber(String phoneNumber);
}
