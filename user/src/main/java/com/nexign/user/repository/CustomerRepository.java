package com.nexign.user.repository;

import com.nexign.user.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByPhoneNumber(String phoneNumber);

    @Query(value = "select c from Customer c join UserCredential u on c.userCredential = u.id where u.id in (:userIds)")
    List<Customer> findByUserIds(@Param("userIds") Collection<Long> userIds);
}
